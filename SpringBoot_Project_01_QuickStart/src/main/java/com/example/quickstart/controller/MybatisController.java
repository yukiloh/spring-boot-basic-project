package com.example.quickstart.controller;

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

        /*
         * springboot中事务开启的方法:
         * 1.在入口Application中开启@EnableTransactionManagement
         * 2.在@Service下的方法上添加@Transactional
         * ※注意：
         * @Transactional只能注解至public上
         *
         * 关于回滚失败：
         * 对于空指针、byZero之类的checked异常可以被回滚
         * 网络失败、文件读写失败等是无法进行回滚的
         *
         * 事务会增加线程开销
         * */

        System.out.println("checking database");
        return userService.findAll();
    }


}