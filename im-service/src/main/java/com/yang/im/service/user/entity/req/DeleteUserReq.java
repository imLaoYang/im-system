package com.yang.im.service.user.entity.req;

import com.yang.im.common.entity.RequestBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteUserReq extends RequestBase {

  // userid集合
  @NotEmpty(message = "用户id不能为空")
  private List<String> userId;

}
