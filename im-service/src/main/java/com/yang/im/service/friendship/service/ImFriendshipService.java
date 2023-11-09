package com.yang.im.service.friendship.service;

import com.yang.im.common.vo.ResponseVO;
import com.yang.im.service.friendship.dto.ImportFriendDTO;
import com.yang.im.service.friendship.entity.ImFriendship;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Yang
* @description 针对表【im_friendship(好友关系表)】的数据库操作Service
* @createDate 2023-11-08 09:15:16
*/
public interface ImFriendshipService extends IService<ImFriendship> {

  /**
   * 引入第三方好友关系
   * 批量导入
   */
  ResponseVO importFriendship(ImportFriendDTO importFriendDTO);

  /**
   * 添加好友
   * @param imFriendship
   * @return
   */
  ResponseVO addFriend(ImFriendship imFriendship);


  /**
   * 删除好友
   * @param imFriendship
   * @return
   */
  ResponseVO deleteFriend(ImFriendship imFriendship);

  ResponseVO updateFriend(ImFriendship imFriendship);
}
