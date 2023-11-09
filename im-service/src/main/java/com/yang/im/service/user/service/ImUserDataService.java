package com.yang.im.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.im.common.vo.ResponseVO;
import com.yang.im.service.user.entity.ImUserData;
import com.yang.im.service.user.dto.DeleteUserDTO;
import com.yang.im.service.user.dto.ImportUserDTO;

public interface ImUserDataService extends IService<ImUserData> {


  /**
   * 导入第三方平台用户数据
   */
  ResponseVO importUser(ImportUserDTO req);

  /**
   * 删除用户
   */
  ResponseVO deleteUser(DeleteUserDTO req);


  ResponseVO getUserInfo(Integer appId);

  ResponseVO getUserInfoByID(String userId, Integer appId);

  ResponseVO alterUser(ImUserData imUserData);
}
