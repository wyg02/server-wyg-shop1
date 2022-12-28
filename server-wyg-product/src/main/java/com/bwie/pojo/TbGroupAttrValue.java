package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 属性值表
 * @TableName tb_group_attr_value
 */
@TableName(value ="tb_group_attr_value")
@Data
public class TbGroupAttrValue implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer valueId;

    /**
     * 属性值
     */
    private String attrValue;

    /**
     * 分组ID
     */
    private Integer attrId;

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