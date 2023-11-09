package com.yang.im.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.im.common.enums.UserErrorCode;
import com.yang.im.common.vo.ResponseVO;
import com.yang.im.service.user.dto.DeleteUserDTO;
import com.yang.im.service.user.dto.ImportUserDTO;
import com.yang.im.service.user.entity.ImUserData;
import com.yang.im.service.user.mapper.ImUserDataMapper;
import com.yang.im.service.user.service.ImUserDataService;
import com.yang.im.service.user.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImUserDataServiceImpl extends ServiceImpl<ImUserDataMapper, ImUserData> implements ImUserDataService {

  /**
   * 导入第三方用户数据
   *
   * @param req
   * @return
   */
  @Override
  public ResponseVO importUser(ImportUserDTO req) {

    UserVO importUserResp = new UserVO();
    // 插入成功的用户id
    List<String> successUserID = new ArrayList<>();
    // 插入失败的用户Id
    List<String> errorUserID = new ArrayList<>();

    if (req.getUserDataList().size() > 100) {
      return ResponseVO.errorResponse(UserErrorCode.IMPORT_SIZE_BEYOND);
    }

    // 批量添加
    List<ImUserData> userDataList = req.getUserDataList();
    for (ImUserData imUserData : userDataList) {
      try {

        imUserData.setAppId(req.getAppId());
        int insert = baseMapper.insert(imUserData);
        if (insert == 1) {
          successUserID.add(imUserData.getUserId());
        }
      } catch (Exception e) {
        e.printStackTrace();
        errorUserID.add(imUserData.getUserId());
      }
    }

    importUserResp.setSuccessId(successUserID);
    importUserResp.setFailId(errorUserID);
    return ResponseVO.successResponse(importUserResp);
  }

  /**
   * 删除用户
   *
   * @param req 用户id列表
   * @return
   */
  @Override
  public ResponseVO deleteUser(DeleteUserDTO req) {
    // 批量删除不能超过100
    if (req.getUserId().size() > 100) {
      return ResponseVO.errorResponse(UserErrorCode.DELETE_USER_ERROR);
    }

    UserVO resp = new UserVO();
    List<String> successUserID = new ArrayList<>();
    List<String> failUserId = new ArrayList<>();

    LambdaUpdateWrapper<ImUserData> wrapper = new LambdaUpdateWrapper<>();

    // 批量删除
    List<String> userId = req.getUserId();
    for (String id : userId) {
      try {
        wrapper.set(ImUserData::getDelFlag, 1);
        wrapper.eq(ImUserData::getUserId, id);
        wrapper.eq(ImUserData::getAppId, req.getAppId());
        boolean update = this.update(wrapper);
        wrapper.clear();
        if (update) {
          // 成功用户id
          successUserID.add(id);
        }
      } catch (Exception e) {
        // 失败用户id
        failUserId.add(id);
      }
    }
    resp.setSuccessId(successUserID);
    resp.setFailId(failUserId);
    return ResponseVO.successResponse(resp);
  }

  /**
   * 获得所有用户信息
   *
   * @param appId 平台id
   * @return
   */
  @Override
  public ResponseVO getUserInfo(Integer appId) {
    LambdaQueryWrapper<ImUserData> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(ImUserData::getAppId, appId);
    List<ImUserData> list = list(wrapper);
    if (list == null) {
      return ResponseVO.errorResponse(UserErrorCode.SERVER_GET_USER_ERROR);
    }

    return ResponseVO.successResponse(list);
  }

  /**
   * 根据id获取
   *
   * @param userId
   * @param appId
   * @return
   */
  @Override
  public ResponseVO getUserInfoByID(String userId, Integer appId) {
    LambdaQueryWrapper<ImUserData> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(ImUserData::getUserId, userId);
    wrapper.eq(ImUserData::getAppId, appId);
    ImUserData imUserData = getOne(wrapper);
    if (imUserData == null) {
      return ResponseVO.errorResponse(UserErrorCode.USER_IS_NOT_EXIST);
    }

    return ResponseVO.successResponse(imUserData);
  }

  @Override
  public ResponseVO alterUser(ImUserData imUserData) {

    LambdaUpdateWrapper<ImUserData> wrapper = new LambdaUpdateWrapper<>();
    wrapper.eq(ImUserData::getAppId,imUserData.getAppId());
    wrapper.eq(ImUserData::getUserId,imUserData.getUserId());
    if (getOne(wrapper) == null) {
      return ResponseVO.errorResponse(UserErrorCode.USER_IS_NOT_EXIST);
    }
    boolean update = update(imUserData, wrapper);

    if (!update) {
      return ResponseVO.errorResponse(UserErrorCode.MODIFY_USER_ERROR);
    }

    return ResponseVO.successResponse("更新用户成功");
  }
}

