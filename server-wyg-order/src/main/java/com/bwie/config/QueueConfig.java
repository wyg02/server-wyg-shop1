package com.bwie.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/21 20:31:20
 */
@Configuration
public class QueueConfig {
    /**
     * This is a dead-letter queue (这是一个订单死信队列)
     * @return
     */
    @Bean
    public Queue OrderDeadQueue(){
        return new Queue("OrderDeadQueue");
    }


    // 死信交换机
    @Bean
    public DirectExchange OrderDeadExchange() {
        return new DirectExchange("OrderDeadExchange");
    }

    // 把死信队列绑定到死信交换机上
    @Bean
    public Binding bindDeadExchange() {
        return BindingBuilder.bind(OrderDeadQueue()).to(OrderDeadExchange()).with("error");
    }

    // 创建业务队列
    @Bean
    public Queue orderQueue() {
        Map<String, Object> args = new HashMap<>(2);
        //--1 过期时间5分钟
        args.put("x-message-ttl", 1000*60*5);

        //--2 x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "OrderDeadExchange");

        //--3x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", "error");

        Queue businessQueue = QueueBuilder.durable("orderQueue").withArguments(args).build();
        return businessQueue;
    }
}
