package com.yang.im.service.user.controller;

import com.yang.im.common.vo.ResponseVO;
import com.yang.im.service.user.entity.ImUserData;
import com.yang.im.service.user.dto.DeleteUserDTO;
import com.yang.im.service.user.dto.ImportUserDTO;
import com.yang.im.service.user.service.ImUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class ImUserController {

  @Autowired
  private ImUserDataService imUserDataService;

  @PostMapping("importUser")
  public ResponseVO importUser(@RequestBody ImportUserDTO importUserDTO) {

    return imUserDataService.importUser(importUserDTO);
  }


  @DeleteMapping("deleteUser")
  public ResponseVO deleteUser(@RequestBody @Validated DeleteUserDTO deleteUserDTO) {
    return imUserDataService.deleteUser(deleteUserDTO);
  }

  @PutMapping("alter")
  public ResponseVO alterUser(@RequestBody ImUserData imUserData) {
    return imUserDataService.alterUser(imUserData);
  }


  @GetMapping("data/getUserInfo/{appId}")
  public ResponseVO getUserInfo(@PathVariable("appId") Integer appId) {

    return imUserDataService.getUserInfo(appId);

  }

  @GetMapping("data/getUserInfo/{userId}/{appId}")
  public ResponseVO getUserInfoById(@PathVariable("userId") String userId, @PathVariable("appId") Integer appId) {
    return imUserDataService.getUserInfoByID(userId, appId);
  }


}
