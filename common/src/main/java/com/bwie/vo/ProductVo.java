package com.bwie.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @Date 2022/11/21 18:12:41
 */
@Data
public class ProductVo implements Serializable {
    /**
     *
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品SPU
     */
    private String productSpu;
    /**
     * 产品类型
     */
    private Integer typeId;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}