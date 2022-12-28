package com.bwie.recv;

import com.alibaba.fastjson2.JSON;
import com.bwie.pojo.TbOrder;
import com.bwie.service.TbOrderService;
import com.bwie.vo.IdVo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/21 20:45:46
 */
@Component
@Slf4j
public class RecvMessage {
    @Resource
    private TbOrderService tbOrderService;
    /**
     * 监听消费 订单死信队列 OrderDeadQueue
     * Message
     */
    @RabbitListener(queues = "OrderDeadQueue")
    public void consumptionOrder(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody());
        log.info("消息体：",body);

        TbOrder tbOrder = JSON.parseObject(body, TbOrder.class);
        //TODO 重复消费
        //获取消息ID
        String messageId = message.getMessageProperties().getMessageId();
        log.info("消息Id：",messageId);

        //取消订单
        IdVo idVo = new IdVo();
        idVo.setId(tbOrder.getOrderId());
        tbOrderService.cancelOrder(idVo);

        //手动消费消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
