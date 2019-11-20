package com.example.AdminLET.controller;

import com.example.AdminLET.domain.Account;
import com.example.AdminLET.domain.Message;
import com.example.AdminLET.domain.UserMessages;
import com.example.AdminLET.domain.UserNotification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping({"/", "login"})
    public String login() {
        return "login";
    }

    @PostMapping({"/login.do"})
    public String login(
            @RequestBody(required = false) String username,
            @RequestBody(required = false) String password) {
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        return "redirect:/index";
    }

    @GetMapping({"/index"})
    public String index() {
        return "index";
    }





    @ResponseBody
    @PostMapping({"/{uuid}/msg"})
    public Message recentMessage(@PathVariable String uuid){
//        System.out.println(uuid);   /*模拟获取uuid*/
        Message message = new Message();
        message.setMessage("hello");/*模拟获取json数据*/
        return message;
    }

    @ResponseBody
    @PostMapping({"/{uuid}/messages"})
    public UserMessages userMessages(@PathVariable String uuid){
        UserMessages userMessages = new UserMessages();
        userMessages.setTotal(3);
        userMessages.setFirst(userMessages.creatFirstUserMessages("Tom", "我下面有人", "今天", "/static/img/user1-128x128.jpg"));
        userMessages.setSecond(userMessages.creatSecondUserMessages("Jerry","我上面有人","今天","/static/img/user5-128x128.jpg"));
        userMessages.setThird(userMessages.creatThirdUserMessages("Spark","我下面有人","今天","/static/img/user6-128x128.jpg"));
        return userMessages;
    }


    @ResponseBody
    @PostMapping({"/{uuid}/notifications"})
    public UserNotification userNotifications(@PathVariable String uuid){
        UserNotification userNotification = new UserNotification();
        userNotification.setMessages(userNotification.creatMessages(1,"今天"));
        userNotification.setResponses(userNotification.creatResponses(2,"今天"));
        userNotification.setReports(userNotification.creatReports(3,"今天"));
        return userNotification;
    }

    @ResponseBody
    @PostMapping({"/accounts"})
    public String accounts(){

        /*后期需要通过数组封装*/
        String accountList = "[" +
                "{\"account\": {\"uuid\": 114514,\"account\": \"admin@admin.com\",\"perm\": \"admin\",\"info\": \"管理员账号\",\"lastLoginGMT\": \"今日\"}}," +
                "{\"account\": {\"uuid\": 1919,\"account\": \"manager@manager.com\",\"perm\": \"manager\",\"info\": \"管理员账号\",\"lastLoginGMT\": \"今日\"}}" +
                "]";
        return accountList;
    }

    /* for test! */
    @GetMapping({"/500"})
    public String fiveZeroZero() {
        return "/error/500";
    }
}