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

    @RequestMapping("/")
    public String quickStart() {
        System.out.println("hello spring boot");
        return "index";
    }

    /**
     * springboot提供的标准greeting演示
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);

        //返回一个greeting.html页面,会默认从resources/下查找,本案例已在yml中设置默认路径为/templates/
        return "greeting";
    }
}
