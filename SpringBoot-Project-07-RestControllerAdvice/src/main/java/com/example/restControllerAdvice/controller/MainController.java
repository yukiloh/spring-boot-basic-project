package com.example.restControllerAdvice.controller;

import com.example.restControllerAdvice.exception.RRException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String myInfo(Model model, Integer id) {
        if (id == null) {
            throw new RRException("id不能为空！",500);
        }

        model.addAttribute("msg",id);
        return "index";
    }
}
