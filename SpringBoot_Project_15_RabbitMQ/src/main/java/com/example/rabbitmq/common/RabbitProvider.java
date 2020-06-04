package com.example.rabbitmq.common;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 生产者
 */
@Component
public class RabbitProvider {

    /**
     * 类似于redisTemplate,由spring进行了封装
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     */
    public void sent(){

        //创建一条内容
        String content = "hello" + new Date();
        System.out.println("provider:" + content);

        //将content发送至hello-rabbit的队列中.如果不填写队列则会发送到默认队列中(特殊队列)
        amqpTemplate.convertAndSend("hello-rabbit",content);
    }
}
