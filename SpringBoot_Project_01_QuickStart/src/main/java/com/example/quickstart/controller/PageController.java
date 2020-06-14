package com.example.quickstart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 演示通过@Controller路由至页面(springMVC的功能)
 */

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        System.out.println("hello spring boot");
        return "index";
    }

}
