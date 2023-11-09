package com.yang.im.service.user.dto;

import com.yang.im.common.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteUserDTO extends BaseDTO {

  // userid集合
  @NotEmpty(message = "用户id不能为空")
  private List<String> userId;


}
