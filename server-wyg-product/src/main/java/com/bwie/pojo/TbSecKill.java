package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 秒杀活动表
 * @TableName tb_sec_kill
 */
@TableName(value ="tb_sec_kill")
@Data
public class TbSecKill implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer killId;

    /**
     * 秒杀活动名称
     */
    private String killName;

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
     * 活动价格  单位分
     */
    private Integer killMoney;

    /**
     * 产品库存
     */
    private Integer wareCount;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 活动状态0：未开始1：已开始 2：活动结束
     */
    private Integer killState;

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