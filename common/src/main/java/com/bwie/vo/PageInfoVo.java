package com.bwie.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageInfoVo implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Integer id;
    private String keyword;
}