package com.bwie.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/25 19:42:23
 */
@Data
public class RoomInfoVo implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer roomId;

    /**
     * 房间编号
     */
    private String roomNo;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date openTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date closeTime;

    /**
     * 推流地址
     */
    private String pushUrl;

    /**
     * 播放地址
     */
    private String playUrl;

    /**
     * 方面图片
     */
    private String coverUrl;

    /**
     * 房间状态0：未开始，1：正在直播，2：直播结束
     */
    private Integer roomState;

    /**
     * 主持人
     */
    private String hostName;

    /**
     * 房间描述
     */
    private String roomDesc;

    /**
     * 删除状态0：未删除，1：已删除
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
