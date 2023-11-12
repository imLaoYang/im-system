package com.yang.im.service.friendship.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.im.common.enums.AllowFriendTypeEnum;
import com.yang.im.common.enums.FriendShipErrorCode;
import com.yang.im.common.enums.FriendShipStatusEnum;
import com.yang.im.common.vo.ResponseVO;
import com.yang.im.service.friendship.controller.vo.FriendVO;
import com.yang.im.service.friendship.dto.ImportFriendDTO;
import com.yang.im.service.friendship.entity.ImFriendship;
import com.yang.im.service.friendship.mapper.ImFriendshipMapper;
import com.yang.im.service.friendship.service.ImFriendshipService;
import com.yang.im.service.user.entity.ImUserData;
import com.yang.im.service.user.service.ImUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang
 * @description 针对表【im_friendship(好友关系表)】的数据库操作Service实现
 * @createDate 2023-11-08 09:15:16
 */
@Service
public class ImFriendshipServiceImpl extends ServiceImpl<ImFriendshipMapper, ImFriendship>
        implements ImFriendshipService {

  @Autowired
  private ImUserDataService imUserDataService;

  /**
   * 引入第三方好友关系
   * 批量导入
   */
  @Override
  public ResponseVO importFriendship(ImportFriendDTO importFriendDTO) {
    List<ImFriendship> imFriendshipList = importFriendDTO.getImFriendshipList();
    // 批量导入不可超100
    if (imFriendshipList.size() > 100) {
      return ResponseVO.errorResponse(FriendShipErrorCode.IMPORT_SIZE_BEYOND);
    }
    FriendVO friendVO = new FriendVO();
    // 插入成功的用户id
    List<String> successID = new ArrayList<>();
    // 插入失败的用户Id
    List<String> failID = new ArrayList<>();


    for (ImFriendship imFriendship : imFriendshipList) {
      try {
        int insert = baseMapper.insert(imFriendship);
        if (insert == 1) {
          successID.add(imFriendship.getToId());
        }
      } catch (Exception e) {
        failID.add(imFriendship.getToId());
      }
    }

    friendVO.setSuccessId(successID);
    friendVO.setFailId(failID);
    return ResponseVO.successResponse(friendVO);
  }

  /**
   * 添加用户
   *
   * @param imFriendship
   * Friend表插入A 和 B 两条记录
   * 查询是否有记录存在，如果存在则判断状态，如果是已添加，则提示已添加，如果是未添加，则修改状态
   */
  @Override
  public ResponseVO addFriend(ImFriendship imFriendship) {


    // 检查用户是否存在
    ResponseVO<ImUserData> fromIdUserVO = imUserDataService.getUserInfoByID(imFriendship.getFromId(), imFriendship.getAppId());
    if (!fromIdUserVO.isOk()) {
      return fromIdUserVO;
    }

    ResponseVO<ImUserData> toIdUserVO = imUserDataService.getUserInfoByID(imFriendship.getToId(), imFriendship.getAppId());
    if (!toIdUserVO.isOk()) {
      return toIdUserVO;
    }

    ImUserData imUserData = toIdUserVO.getData();
    // 加好友不需要验证
    if (imUserData.getFriendAllowType() == AllowFriendTypeEnum.NOT_NEED.getCode()) {
      // 判断好友关系是否存在
      ImFriendship friendship = getImFriendship(imFriendship);

      if (friendship == null) {
        imFriendship.setCreateTime(System.currentTimeMillis());
        this.save(imFriendship);
        return ResponseVO.successResponse("添加好友成功");
      }

      // 未添加好友
      if (friendship.getStatus() == FriendShipStatusEnum.FRIEND_STATUS_NO_FRIEND.getCode()) {
        LambdaUpdateWrapper<ImFriendship> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ImFriendship::getStatus, System.currentTimeMillis());
        updateWrapper.set(ImFriendship::getCreateTime, Timestamp.from(Instant.now()).getTime());
        updateWrapper.eq(ImFriendship::getAppId, imFriendship.getAppId());
        updateWrapper.eq(ImFriendship::getFromId, imFriendship.getFromId());
        updateWrapper.eq(ImFriendship::getToId, imFriendship.getToId());
        if (update(imFriendship, updateWrapper)) {
          return ResponseVO.successResponse("添加好友成功");
        }
      }

      // 判断好友状态
      if (friendship.getStatus() == FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode()) {
        return ResponseVO.errorResponse(FriendShipErrorCode.TO_IS_YOUR_FRIEND);
      }
    }else {
      // todo 发起添加好友请求



    }


    return ResponseVO.errorResponse("添加失败");
  }

  /**
   * 删除好友
   *
   * @param imFriendship
   * @return
   */
  @Override
  public ResponseVO deleteFriend(ImFriendship imFriendship) {

    ImFriendship friendship = getImFriendship(imFriendship);

    if (friendship == null) {
      return ResponseVO.errorResponse(FriendShipErrorCode.TO_IS_NOT_YOUR_FRIEND);
    }

    if (friendship.getStatus() == FriendShipStatusEnum.FRIEND_STATUS_DELETE.getCode()) {
      return ResponseVO.errorResponse(FriendShipErrorCode.FRIEND_IS_DELETED);
    }


    LambdaUpdateWrapper<ImFriendship> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(ImFriendship::getStatus, FriendShipStatusEnum.FRIEND_STATUS_DELETE.getCode());
    updateWrapper.eq(ImFriendship::getAppId, imFriendship.getAppId());
    updateWrapper.eq(ImFriendship::getFromId, imFriendship.getFromId());
    updateWrapper.eq(ImFriendship::getToId, imFriendship.getToId());
    boolean update = this.update(updateWrapper);
    if (update) {
      return ResponseVO.successResponse("删除好友成功");
    }

    return ResponseVO.errorResponse();
  }

  /**
   * 更新
   *
   * @param imFriendship
   * @return
   */
  @Override
  public ResponseVO updateFriend(ImFriendship imFriendship) {
    // 查询user_data表是否有用户
    ResponseVO<ImUserData> fromIdUserVO = imUserDataService.getUserInfoByID(imFriendship.getFromId(), imFriendship.getAppId());
    if (!fromIdUserVO.isOk()) {
      return fromIdUserVO;
    }

    ResponseVO<ImUserData> toIdUserVO = imUserDataService.getUserInfoByID(imFriendship.getToId(), imFriendship.getAppId());
    if (!toIdUserVO.isOk()) {
      return toIdUserVO;
    }

    // 更新
    LambdaUpdateWrapper<ImFriendship> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(ImFriendship::getAddSource, imFriendship.getAddSource())
            .set(ImFriendship::getExtra, imFriendship.getExtra())
            .set(ImFriendship::getRemark, imFriendship.getRemark())
            .eq(ImFriendship::getAppId, imFriendship.getAppId())
            .eq(ImFriendship::getToId, imFriendship.getToId())
            .eq(ImFriendship::getFromId, imFriendship.getFromId());

    int update = baseMapper.update(imFriendship, updateWrapper);
    if (update == 1) {
      return ResponseVO.successResponse("更新成功");
    }

    return ResponseVO.errorResponse("更新失败");
  }


  /**
   * 数据库查询imFriendship对象
   */
  private ImFriendship getImFriendship(ImFriendship imFriendship) {
    LambdaQueryWrapper<ImFriendship> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(ImFriendship::getAppId, imFriendship.getAppId());
    wrapper.eq(ImFriendship::getFromId, imFriendship.getFromId());
    wrapper.eq(ImFriendship::getToId, imFriendship.getToId());
    return this.getOne(wrapper);
  }

}




