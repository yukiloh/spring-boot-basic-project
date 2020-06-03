package com.example.easymybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan({"com.example.easymybatis.mapper"})     //在入口类开启mapper扫描,tk.mybatis的注解
@SpringBootApplication
public class EasyMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyMybatisApplication.class, args);
    }

}
