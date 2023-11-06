package com.yang.im.service.user.controller;

import com.yang.im.common.vo.ResponseVO;
import com.yang.im.service.user.entity.req.DeleteUserReq;
import com.yang.im.service.user.entity.req.ImportUserReq;
import com.yang.im.service.user.service.ImUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class ImportUserController {

  @Autowired
  private ImUserDataService imUserDataService;

  @PostMapping("importUser")
  public ResponseVO importUser(@RequestBody ImportUserReq req, Integer appId) {

    req.setAppId(appId);
    return imUserDataService.importUser(req);
  }

  @DeleteMapping("deleteUser")
  public ResponseVO deleteUser(@RequestBody @Validated DeleteUserReq req, Integer appId) {
    req.setAppId(appId);
    return imUserDataService.deleteUser(req);
  }

}
