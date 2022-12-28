package com.bwie.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/22 19:13:42
 */
@Data
public class PayWeChatOrderVo implements Serializable {
    private Long outTradeNo;
    private String description;
    private Long amount;
    private String attach;
}
