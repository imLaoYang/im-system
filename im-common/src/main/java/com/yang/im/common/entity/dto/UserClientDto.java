package com.yang.im.common.entity.dto;

import lombok.Data;


@Data
public class UserClientDto {

    private Integer appId;

    private Integer clientType;

    private String userId;

    private String imei;

}