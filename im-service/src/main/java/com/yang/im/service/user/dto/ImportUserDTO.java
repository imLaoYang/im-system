package com.yang.im.service.user.dto;

import com.yang.im.common.dto.BaseDTO;
import com.yang.im.service.user.entity.ImUserData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 引入用户数据 请求实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImportUserDTO extends BaseDTO {

  // 用户数据列表
  private List<ImUserData> userDataList;



}
