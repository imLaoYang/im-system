package com.yang.im.service.friendship.controller;

import com.yang.im.common.vo.ResponseVO;
import com.yang.im.service.friendship.dto.ImportFriendDTO;
import com.yang.im.service.friendship.entity.ImFriendship;
import com.yang.im.service.friendship.service.ImFriendshipService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("friendship")
public class FriendshipController {


  @Resource
  private ImFriendshipService imFriendshipService;

  /**
   * 引入第三方好友关系
   * 批量导入
   */
  @PostMapping("import")
  public ResponseVO importFriendship(@RequestBody @Validated ImportFriendDTO importFriendDTO){
      return imFriendshipService.importFriendship(importFriendDTO);
  }

  @PostMapping("addFriend")
  public ResponseVO addFriend(@RequestBody @Validated ImFriendship imFriendship){
    return imFriendshipService.addFriend(imFriendship);
  }
  @PutMapping("updateFriend")
  public ResponseVO updateFriend(@RequestBody @Validated  ImFriendship imFriendship){
      return imFriendshipService.updateFriend(imFriendship);
  }

  @DeleteMapping("deleteFriend")
  public ResponseVO deleteFriend(@RequestBody @Validated  ImFriendship imFriendship){
    return imFriendshipService.deleteFriend(imFriendship);
  }



//  @GetMapping("getFriend")
//  public ResponseVO getFriend(){
//    return imFriendshipService.getFriend();
//  }
//

}
