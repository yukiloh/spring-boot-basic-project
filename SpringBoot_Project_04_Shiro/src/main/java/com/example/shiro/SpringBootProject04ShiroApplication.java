package com.example.shiro;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@RequiresAuthentication
@SpringBootApplication
public class SpringBootProject04ShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProject04ShiroApplication.class, args);
    }

}
