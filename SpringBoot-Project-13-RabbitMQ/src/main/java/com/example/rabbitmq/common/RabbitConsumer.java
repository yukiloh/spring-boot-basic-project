package com.example.rabbitmq.common;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * 消费者
 */
@Component
@RabbitListener(queues = "hello-rabbit")     //对"hello-rabbit"队列进行消息监听
public class RabbitConsumer {

    //接受消息
    @RabbitHandler
    public void process(String content){
        System.out.println("consumer:" + content);
    }
}
