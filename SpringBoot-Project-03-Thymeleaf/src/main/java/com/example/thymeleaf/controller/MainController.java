package com.example.thymeleaf.controller;

import com.example.thymeleaf.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class MainController {

    /**
     * 用于给index.html传值
     */
    @GetMapping("/")
    public String context01(Model model, HttpServletRequest request){
        User user = new User();
        user.setName("zhangsan");
        user.setAge(12);
        user.setGender("1");
        User user2 = new User();
        user2.setName("lisi");
        user2.setAge(11);
        user2.setGender("0");


        ArrayList<Object> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);

        model.addAttribute("msg","hello thymeleaf");
        model.addAttribute("user",user);
        model.addAttribute("userList",userList);
        model.addAttribute("url","www.bing.com");
        model.addAttribute("testOrder","1");
        model.addAttribute("date",new Date());
        model.addAttribute("max",null);

        //单次页面访问使用的request
        request.setAttribute("testRequest","here is test request!");
        //多次页面访问使用,会保存在会话中
        request.getSession().setAttribute("testSession","here is test session!");
        return "index";
    }

    /**
     * 向前台返回json数据
     * @return
     */
    @GetMapping("/test/1")
    @ResponseBody
    public String context01(){
        return "{'msg':'success!'}";
    }

    /**
     * 跳转,不同于重定向,可以保留request
     * @return
     */
    @GetMapping("/test/2")
    public String context02(){
        return "forward:/test/3";
    }

    @GetMapping("/test/3")
    @ResponseBody
    public String context03(){
        return "success!";
    }

}
