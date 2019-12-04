package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestContext {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void contextLoads() {
        System.out.println(redisTemplate.opsForValue().get("f1d84769-1905-4b39-87ea-1e91f309af22"));
    }

}
