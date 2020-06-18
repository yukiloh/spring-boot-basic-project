package com.example.rabbitmq;

import com.example.rabbitmq.common.RabbitProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class RabbitMQApplicationTests {

    @Autowired
    private RabbitProvider rabbitProvider;


    /**
     * 测试发送消息
     */
    @Test
    void contextLoads() {
        for (int i = 0; i < 50; i++) {
            rabbitProvider.sent();

        }
    }

    @Test
    void contextLoads1() {

    }

}
