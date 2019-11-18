package com.example.AdminLET.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping({"/","login"})
    public String login(){
        return "login";
    }

    @PostMapping({"/index"})
    public String index(){ return "index"; }}