package com.bwie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.pojo.TbUser;
import com.bwie.service.TbUserService;
import com.bwie.mapper.TbUserMapper;
import com.bwie.utils.ResultResponse;
import com.bwie.utils.TokenUtils;
import com.bwie.vo.LoginInfoVo;
import com.bwie.vo.UserVo;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
* @author 魏阳光
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2022-12-20 17:10:42
*/
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService{

    @Value("${site.token.tokenPassword}")
    private String tokenPassword;

    @Value("${site.token.expireTime}")
    private Integer expireTime;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResultResponse add(UserVo userVo) {
        //查询用户是否存在
        LambdaQueryWrapper<TbUser> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(TbUser::getUserName,userVo.getUserName());
        TbUser one = baseMapper.selectOne(wrapper.last("limit 1"));
        if (one!=null){
            return ResultResponse.FAILED().message("用户已经存在");
        }
        //生成密码
        TbUser tbUser = new TbUser();
        //将userVo的属性数据 复制 tbUser一份
        BeanUtils.copyProperties(userVo,tbUser);

        String hashpw = BCrypt.hashpw(userVo.getUserPass(), BCrypt.gensalt());
        tbUser.setUserPass(hashpw);
        baseMapper.insert(tbUser);
        return ResultResponse.SUCCESS();
    }

    /**
     * 用户登陆
     * @param loginInfoVo
     * @return
     */
    @Override
    public ResultResponse login(LoginInfoVo loginInfoVo) {
        //查询用户是否存在
        LambdaQueryWrapper<TbUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbUser::getUserName,loginInfoVo.getUserName());
        TbUser one = baseMapper.selectOne(wrapper.last("limit 1"));
        if (one==null){
            return ResultResponse.FAILED().message("用户不存在");
        }
        //校验密码
        if(!BCrypt.checkpw(loginInfoVo.getUserPwd(),one.getUserPass())){
            return ResultResponse.FAILED().message("密码错误");
        }
        //生成Token 存入Redis
        String token = TokenUtils.token()
                .setKey(tokenPassword)
                .setExpire(expireTime)
                .setClaim("userId", one.getUserId().toString())
                .setClaim("userName", one.getUserName())
                .makeToken();
        redisTemplate.opsForValue().set(token,token,5, TimeUnit.MINUTES);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(one,userVo);
        userVo.setToken(token);
        userVo.setUserPass("***********");
        return ResultResponse.SUCCESS().data("user",userVo).data("token",token);
    }
}




