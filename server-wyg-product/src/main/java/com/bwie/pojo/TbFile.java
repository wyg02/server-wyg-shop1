package com.bwie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 上传文件表
 * @TableName tb_file
 */
@TableName(value ="tb_file")
@Data
public class TbFile implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer fileId;

    /**
     * 
     */
    private Integer userId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 
     */
    private Long fileSize;

    /**
     * 文件路径
     */
    private String fileUrl;

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