package com.yang.im.common.dto;

import lombok.Data;


@Data
public class BaseDTO {

    // 平台id
    private Integer appId;

    private String operater;

    // 客户端类型
    private Integer clientType;

    private String imei;
}
