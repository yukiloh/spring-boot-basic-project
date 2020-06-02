package com.example.restControllerAdvice.controller;

import com.example.restControllerAdvice.exception.RRException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("myInfo")
    public String myInfo(@RequestParam Integer id) {
        if (id == null) {
            throw new RRException("id不能为空！",500);
        }
        return "myInfo";
    }
}
