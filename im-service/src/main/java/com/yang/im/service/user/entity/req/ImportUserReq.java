package com.yang.im.service.user.entity.req;

import com.yang.im.common.entity.RequestBase;
import com.yang.im.service.user.entity.ImUserData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 引入用户数据 请求实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImportUserReq extends RequestBase {

  // 用户数据列表
  private List<ImUserData> userDataList;
}
