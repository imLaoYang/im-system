package com.yang.im.service.friendship.dto;

import com.yang.im.common.dto.BaseDTO;
import com.yang.im.service.friendship.entity.ImFriendship;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImportFriendDTO extends BaseDTO {

  List<ImFriendship> imFriendshipList;

}
