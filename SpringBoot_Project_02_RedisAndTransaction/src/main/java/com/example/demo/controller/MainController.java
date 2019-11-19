package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {


    @RequestMapping("/")
    public String quickStart() {
        System.out.println("hello spring boot");
        return "index";
    }

    /*springboot提供的标准greeting演示*/
    @GetMapping("/greeting")        /*@GetMapping = @RequestMapping(method = RequestMethod.GET)*/
    /*@RequestParam中,required:可以不进行传参,并且defaultValue指定为World*/
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";  /*返回一个greeting.html页面,会默认从resources/t/下查找; 方法上不可添加@ResponseBody!!*/
    }

}
