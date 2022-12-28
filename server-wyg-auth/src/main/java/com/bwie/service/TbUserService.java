package com.bwie.service;

import com.bwie.pojo.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.LoginInfoVo;
import com.bwie.vo.UserVo;

/**
* @author 魏阳光
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2022-12-20 17:10:42
*/
public interface TbUserService extends IService<TbUser> {

    ResultResponse add(UserVo userVo);

    ResultResponse login(LoginInfoVo loginInfoVo);
}
