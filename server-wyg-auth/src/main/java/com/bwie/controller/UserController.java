package com.bwie.controller;

import com.bwie.service.TbUserService;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.LoginInfoVo;
import com.bwie.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/20 17:11:02
 */
@Api(tags = "用户认证-APi")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private TbUserService tbUserService;

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public ResultResponse add(@Valid @RequestBody UserVo userVo){
        return tbUserService.add(userVo);
    }

    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public ResultResponse login(@Valid @RequestBody LoginInfoVo loginInfoVo){
        return tbUserService.login(loginInfoVo);
    }



}
