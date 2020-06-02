package com.example.quickstart.controller;

import com.example.quickstart.dao.IUserDao;
import com.example.quickstart.domain.User;
import com.example.quickstart.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MybatisController {
    /**
     * 通过接入mybatis,来获取数据库的结果,并返回至前端
     */

    @Autowired
    private UserService userService;

    @GetMapping("/check-database")
    public List<User> quickQuery(){
        System.out.println("checking database");
        return userService.findAll();
    }


}