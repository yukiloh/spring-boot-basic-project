package com.example.demo;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@RequiresAuthentication
@SpringBootApplication
public class SpringBootProject04ShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProject04ShiroApplication.class, args);
    }

}
