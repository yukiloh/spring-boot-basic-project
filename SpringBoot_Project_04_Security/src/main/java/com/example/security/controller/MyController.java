package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 13:11
 */
@RestController
public class MyController {

    @GetMapping({"/","/index"})
    public String home() {
        System.out.println("index");
        return "index页面,可以访问 /user /admin 来测试认证,每个页面对应不同的角色权限,user只能访问user页面,admin2个都可以";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user页面";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin页面";
    }

}
