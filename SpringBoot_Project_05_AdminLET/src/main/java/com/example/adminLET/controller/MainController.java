package com.example.adminLET.controller;

import com.example.adminLET.domain.*;
import com.example.adminLET.service.PostService;
import com.example.adminLET.service.UserService;
import com.example.adminLET.utils.MapperUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        /*模拟验证用户名密码*/
        System.out.println("username:" + username);
        System.out.println("password:" + password);

        return "redirect:/index";
    }

    @GetMapping({"/index"})
    public String index() {
        return "index";
    }

    @GetMapping({"/adminList"})
    public String adminList() {
        return "adminList";
    }

    @GetMapping({"/postList"})
    public String postList() {
        return "postList";
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

    @Autowired
    private UserService userService;

    /*获取管理员列表*/
    @ResponseBody
    @PostMapping({"/admins"})
    public String admins(){
        UserTable userTable = null;
        PageInfo<UserTable> page = userService.page(1, 10, userTable);
        List<UserTable> userTables = page.getList();
//        List<UserTable> userTables = userService.selectAll(); /*已替换为使用pageHelper获取list*/
        try {
            String result = MapperUtils.obj2json(userTables);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bad answer";
    }

    @Autowired
    private PostService postService;

    /*获取文章列表*/
    @ResponseBody
    @PostMapping({"/posts"})
    public String posts(){
        PostsTable postsTable = null;
        PageInfo<PostsTable> page = postService.page(1, 10, postsTable);
        List<PostsTable> postsTables = page.getList();
//        List<UserTable> userTables = userService.selectAll(); /*已替换为使用pageHelper获取list*/
        try {
            String result = MapperUtils.obj2json(postsTables);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bad answer";
    }

    /*进入文章单页*/
    @GetMapping({"/post/{guid}"})
    public String post(@PathVariable String guid, Model model) {
        PostsTable postsTable = postService.selectOne(guid);
        model.addAttribute("title",postsTable.getTitle());
        model.addAttribute("main",postsTable.getMain());
        model.addAttribute("timePublished",postsTable.getTimePublished());
        return "post";
    }


    /* for test! */
    @GetMapping({"/500"})
    public String fiveZeroZero() {
        return "/error/500";
    }


}