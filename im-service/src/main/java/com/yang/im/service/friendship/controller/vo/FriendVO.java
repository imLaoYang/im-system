package com.yang.im.service.friendship.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * im_user_data表controller的response
 */
@Data
public class FriendVO {

  // 成功导入的id
  private List<String> successId;

  // 失败导入的Id
  private List<String> failId;
}
