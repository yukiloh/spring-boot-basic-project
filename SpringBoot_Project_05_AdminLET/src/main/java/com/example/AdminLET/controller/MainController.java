package com.example.AdminLET.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {

    @GetMapping({"/","login"})
    public String login(){
        return "login";
    }

    @PostMapping({"/index"})
    public String index(
            @RequestBody(required = false) String username,
            @RequestBody(required = false) String password){
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        return "index";
    }}