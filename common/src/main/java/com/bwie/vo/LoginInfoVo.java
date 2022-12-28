package com.bwie.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @Date 2022/11/14 20:27:26
 */
@Data
public class LoginInfoVo implements Serializable {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    private String userPwd;
    private String code;
}
