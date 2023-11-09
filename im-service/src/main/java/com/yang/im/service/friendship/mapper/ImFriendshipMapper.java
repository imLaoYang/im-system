package com.yang.im.service.friendship.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.im.service.friendship.entity.ImFriendship;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Yang
* @description 针对表【im_friendship(好友关系表)】的数据库操作Mapper
* @createDate 2023-11-08 09:15:16
* @Entity com.yang.im.service.domain.ImFriendship
*/

@Mapper
public interface ImFriendshipMapper extends BaseMapper<ImFriendship> {

}




