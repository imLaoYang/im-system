package com.yang.im.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.im.common.vo.ResponseVO;
import com.yang.im.service.user.entity.ImUserData;
import com.yang.im.service.user.entity.req.DeleteUserReq;
import com.yang.im.service.user.entity.req.ImportUserReq;

public interface ImUserDataService extends IService<ImUserData> {


  /**
   * 导入第三方平台用户数据
   */
  ResponseVO importUser(ImportUserReq req);

  /**
   * 删除用户
   */
  ResponseVO deleteUser(DeleteUserReq req);

}
