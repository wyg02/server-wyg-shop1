package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品SKU
 * @TableName tb_product_sku
 */
@TableName(value ="tb_product_sku")
@Data
public class TbProductSku implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer skuId;

    /**
     * SKU_CODE
     */
    private String skuCode;

    /**
     * SPU_CODE
     */
    private String spuCode;

    /**
     * 产品价格 单位 分
     */
    private Integer productPrice;

    /**
     * 产品描述
     */
    private String productDesc;

    /**
     * 删除状态0：未删除1：已删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}