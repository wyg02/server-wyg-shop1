package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 属性表
 * @TableName tb_group_attr
 */
@TableName(value ="tb_group_attr")
@Data
public class TbGroupAttr implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer attrId;

    /**
     * 属性名字
     */
    private String attrName;

    /**
     * 分组ID
     */
    private Integer groupId;

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