package com.yang.im.service.user.entity.resp;

import lombok.Data;

import java.util.List;

/**
 * 导入用户的response
 */
@Data
public class ImportUserResp {

  // 成功的用户id
  private List<String> successId;

  // 失败的用户Id
  private List<String> errorId;
}
