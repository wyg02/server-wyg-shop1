package com.bwie.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/22 21:05:56
 */
@Data
public class ResultVo implements Serializable {
    private boolean success;
    private int code;
    private String message;
    private String codeUrl;
    private Object data;
}
