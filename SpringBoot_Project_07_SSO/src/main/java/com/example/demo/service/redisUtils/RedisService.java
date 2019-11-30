package com.example.demo.service.redisUtils;

import org.springframework.stereotype.Service;


@Service
/*  从redis中获取数据 */

public interface RedisService {

    Boolean put(String key, String value, long seconds);

    String get(String key);



}
