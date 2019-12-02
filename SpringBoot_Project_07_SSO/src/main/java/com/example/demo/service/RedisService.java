package com.example.demo.service;


import org.springframework.stereotype.Service;

@Service
/*  从redis中获取数据 */
public interface RedisService {

    void put(String key, String value, long seconds);

    String get(String key);


    Boolean delete(String key);

    void refresh(String key);



}
