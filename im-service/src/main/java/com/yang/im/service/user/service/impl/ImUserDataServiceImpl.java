package com.yang.im.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.im.common.vo.ResponseVO;
import com.yang.im.common.enums.UserErrorCode;
import com.yang.im.service.user.entity.ImUserData;
import com.yang.im.service.user.entity.req.DeleteUserReq;
import com.yang.im.service.user.entity.req.ImportUserReq;
import com.yang.im.service.user.entity.resp.ImportUserResp;
import com.yang.im.service.user.mapper.ImUserDataMapper;
import com.yang.im.service.user.service.ImUserDataService;
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
  public ResponseVO importUser(ImportUserReq req) {

    ImportUserResp importUserResp = new ImportUserResp();
    // 插入成功的用户id
    List<String> successUserID = new ArrayList<>();
    // 插入失败的用户Id
    List<String> errorUserID = new ArrayList<>();

    if (req.getUserDataList().size() > 100) {
      return ResponseVO.errorResponse(UserErrorCode.IMPORT_SIZE_BEYOND);
    }


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
    importUserResp.setErrorId(errorUserID);
    return ResponseVO.successResponse(importUserResp);
  }

  /**
   * 删除用户
   *
   * @param req
   * @return
   */
  @Override
  public ResponseVO deleteUser(DeleteUserReq req) {

    baseMapper.deleteBatchIds(req.getUserId());
    return ResponseVO.successResponse();
  }
}