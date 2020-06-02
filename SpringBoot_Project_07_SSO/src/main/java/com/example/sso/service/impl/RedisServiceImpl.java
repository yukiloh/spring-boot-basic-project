package com.example.sso.service.impl;

import com.example.sso.constants.WebConstants;
import com.example.sso.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

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
        while (!tryAgain || i > 3){
            String result = redisTemplate.opsForValue().get(key);
            if (result == null) {
                tryAgain = true;
                i++;
            }else {
                try {
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        /*当重试后依然获取不到时返回null*/
        return null;
    }

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void refresh(String key) {
        redisTemplate.expire(key, WebConstants.QUATER_DAY,TimeUnit.SECONDS);
    }


}