package com.yang.im.service.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户数据表
 * @author: : 数据库用户数据实体类
 **/
@Data
@TableName("im_user_data")
public class ImUserData implements Serializable {


    // 用户id
    private String userId;

    // 平台id
    private Integer appId;


    // 用户名称
    private String nickName;

    //位置
    private String location;

    //生日
    private String birthday;

    private String password;

    // 头像
    private String photo;

    // 性别
    private Integer userSex;

    // 个性签名
    private String selfSignature;

    // 加好友验证类型（Friend_AllowType） 1需要验证
    private Integer friendAllowType;

    // 管理员禁止用户添加加好友：0 未禁用 1 已禁用
    private Integer disableAddFriend;

    // 禁用标识(0 未禁用 1 已禁用)
    private Integer forbiddenFlag;

    // 禁言标识
    private Integer silentFlag;
    /**
     * 用户类型 1普通用户 2客服 3机器人
     */
    private Integer userType;



    // 逻辑删
    @TableLogic(delval = "1")
    private Integer delFlag;

    private String extra;

}
