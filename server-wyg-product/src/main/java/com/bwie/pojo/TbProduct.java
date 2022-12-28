package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品表
 * @TableName tb_product
 */
@TableName(value ="tb_product")
@Data
public class TbProduct implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品类型
     */
    private Integer typeId;

    /**
     * 产品SPU
     */
    private String productSpu;

    /**
     * 产品封面
     */
    private String productCover;

    /**
     * 产品描述
     */
    private String productDesc;

    /**
     * 评论总数
     */
    private Integer commentCount;

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