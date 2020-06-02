package com.example.quickstart.controller;

import com.example.quickstart.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController     //@Controller和@ResponseBody的结合
public class QuickstartController {

    /**
     * 基础演示.通过@RequestParam接受地址中名为word的变量(如果没有则使用默认值"world",在jvm中打印,并返回字符串至前端)
     */
    @GetMapping("/quickstart")
    public String quickQuery(@RequestParam(value = "word", required=false, defaultValue = "world") String word){
        String msg = "hello "+word;
        System.out.println(msg);
        return
                msg
                +"<br>"
                +"you can input  ?word=yourName  to change the result";
    }

    /**
     * 通过@Autowired来导入service对象,并使用其下的方法
     */

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String testService(String msg){      //不使用@RequestParam也可以接收地址栏的参数,但无法自定义默认值
        return testService.TestDoSomething(msg);
    }
}
