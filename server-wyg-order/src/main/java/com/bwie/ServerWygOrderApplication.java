package com.bwie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.bwie.mapper"})
public class ServerWygOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerWygOrderApplication.class, args);
    }

}
