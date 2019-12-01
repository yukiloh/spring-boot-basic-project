package com.example.demo.service.impl;

import com.example.demo.service.RedisService;
import com.example.demo.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public void put(String key, String value, long seconds) {
        /*序列化key，使得key不为乱码*/
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(key, value,seconds, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        /*因为出现redis读取失败的问题，设置了重试机制*/
        boolean tryAgain = false;
        int i = 0;
        while (!tryAgain || i > 5){
            Object o = redisTemplate.opsForValue().get(key);
            if (o == null) {
                tryAgain = true;
                i++;
            }else {
                try {
                    return MapperUtils.obj2json(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        /*当重试后依然获取不到时返回null*/
        return null;
    }
}
