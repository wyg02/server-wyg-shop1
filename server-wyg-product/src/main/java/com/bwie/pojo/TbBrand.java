package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 品牌表
 * @TableName tb_brand
 */
@TableName(value ="tb_brand")
@Data
public class TbBrand implements Serializable {
    /**
     * 品牌ID
     */
    @TableId(type = IdType.AUTO)
    private Integer brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌Logo
     */
    private String brandLogo;

    /**
     * 显示0：显示1：隐藏
     */
    private Integer brandHide;

    /**
     * 品牌首字母
     */
    private String brandLetter;

    /**
     * 品牌顺序
     */
    private Integer brandSort;

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