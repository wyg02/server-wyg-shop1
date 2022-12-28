package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品库存表
 * @TableName tb_ware
 */
@TableName(value ="tb_ware")
@Data
public class TbWare implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer wareId;

    /**
     * 产品SKU
     */
    private String sku;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 产品库存
     */
    private Integer wareCount;

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