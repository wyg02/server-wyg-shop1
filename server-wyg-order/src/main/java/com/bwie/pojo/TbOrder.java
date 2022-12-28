package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单表
 * @TableName tb_order
 */
@TableName(value ="tb_order")
@Data
public class TbOrder implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    /**
     * 订单编号
     */
    private Long orderNo;

    /**
     * 秒杀活动名称
     */
    private String killName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 秒杀产品名称
     */
    private String productName;

    /**
     * 秒杀产品SKU
     */
    private String productSku;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 产品数量
     */
    private Integer productCount;

    /**
     * 订单金额
     */
    private Long orderAmount;

    /**
     * 订单状态0：未支付1：已支付 2：取消支付 3：已发货
     */
    private Integer orderState;

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