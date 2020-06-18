package com.example.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ的配置类
 */
@Configuration
public class RabbitConfiguration {

    /**
     * 创建一个队列.注意Queue的依赖位置
     * 由spring提供
     */
    @Bean
    public Queue createQueue(){

        //创建一个名为"hello-rabbit"的队列
        return new Queue("hello-rabbit");
    }
}
