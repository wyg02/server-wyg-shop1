package com.bwie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.pojo.TbUser;
import com.bwie.service.TbUserService;
import com.bwie.mapper.TbUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 魏阳光
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2022-12-21 19:56:52
*/
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService{

}




