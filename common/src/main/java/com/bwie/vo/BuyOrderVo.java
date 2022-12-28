package com.bwie.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/21 20:04:47
 */
@Data
public class BuyOrderVo implements Serializable {
    private Integer userId;
    private Integer productId;
    private String sku;
    private String spu;
    private Long productPrice;
    private Integer productCount;
    private Long amount;
}
