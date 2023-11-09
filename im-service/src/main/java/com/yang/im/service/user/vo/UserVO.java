package com.yang.im.service.user.vo;

import lombok.Data;

import java.util.List;

/**
 * im_user_data表controller的response
 */
@Data
public class UserVO {

  // 成功的用户id
  private List<String> successId;

  // 失败的用户Id
  private List<String> failId;
}
