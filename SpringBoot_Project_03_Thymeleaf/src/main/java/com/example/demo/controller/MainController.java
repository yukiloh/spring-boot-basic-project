package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class MainController {

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

        /*单次页面访问*/
        request.setAttribute("testRequest","here is test text!");
        /*多次页面,保存在会话中*/
        request.getSession().setAttribute("testRequest","here is test text!");
        return "index";
    }

    @GetMapping("/test/1")
    public @ResponseBody String context01(){
        return "{'msg':'success!'}";
    }

    @GetMapping("/test/2")
    public String context02(){
        return "forward:/test/3";
    }

    @GetMapping("/test/3")
    @ResponseBody
    public String context03(){
        return "success!";
    }


    @GetMapping("/login")
    public String context04(){
        return "login";
    }

    @GetMapping("/video")
    public String context05() {
        return "video";
    }
}
