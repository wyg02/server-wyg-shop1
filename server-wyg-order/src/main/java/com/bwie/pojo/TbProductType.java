package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 产品分类表
 * @TableName tb_product_type
 */
@TableName(value ="tb_product_type")
@Data
public class TbProductType implements Serializable {
    /**
     * 类型ID
     */
    @TableId(type = IdType.AUTO)
    private Integer typeId;

    /**
     * 分类名称
     */
    private String typeName;

    /**
     * 父级ID
     */
    private Integer typePid;

    /**
     * 显示顺序
     */
    private Integer typeSort;

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