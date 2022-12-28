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
 * @date 2022/12/20 17:22:08
 */
@Data
public class UserVo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 登录密码
     */
    private String userPass;

    /**
     * 手机号
     */
    private String userMobile;
    /**
     * 登陆令牌
     */
    private String token;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别，0未知1男2女
     */
    private Integer userSex;

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
