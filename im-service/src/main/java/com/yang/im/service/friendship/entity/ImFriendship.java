package com.yang.im.service.friendship.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 好友关系表
 * @TableName im_friendship
 */
@TableName(value ="im_friendship")
@Data
public class ImFriendship implements Serializable {
    /**
     * 平台
     */
    @NotEmpty(message = "appId不能为空")
    private Integer appId;

    /**
     * from_id
     */
    @NotEmpty(message = "fromId不能为空")
    private String fromId;

    /**
     * to_id
     */
    @NotEmpty(message = "toId不能为空")
    private String toId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态 0未添加 1正常 2删除
     */
    @NotNull(message = "status不能为空")
    private Integer status;

    /**
     * 1正常 2拉黑
     */
    private Integer black;

    /**
     * 
     */
    private Long createTime;

    /**
     * 
     */
    private Long friendSequence;

    /**
     * 
     */
    private Long blackSequence;

    /**
     * 来源
     */
    private String addSource;

    /**
     * 来源
     */
    private String extra;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}