package com.bwie.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @Date 2022/11/29 9:33:55
 */
@Configuration
public class RedissionConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;


    @Bean
    public RedissonClient getRedisson(){

        Config config = new Config();
        //单机模式  依次设置redis地址和密码
        config.useSingleServer().
                setAddress("redis://" + host + ":" + port);
        return Redisson.create(config);
    }
}
