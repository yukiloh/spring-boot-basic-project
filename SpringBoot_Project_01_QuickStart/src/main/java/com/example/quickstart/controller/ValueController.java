package com.example.quickstart.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ConfigurationProperties(prefix = "area")       //用于匹配properties中的前缀,并可以通过get set来获取该值
@PropertySource("classpath:application.yml")    //用于指定@Value所读取的配置文件,不设置则默认为application.properties/yml
public class ValueController {

    /**
     * 演示如何从配置文件(application.yml)中获取value
     */

    @Value("${name}")
    private String myName;

    //获取数组中的某一个值
    @Value("${city[0]}")
    private String  city;

    //获取二级变量
    @Value("${area.name}")
    private String  areaName;

    //通过@ConfigurationProperties中,设置前缀prefix来获取area中的name和location,使用时需要设置get&set方法
    private String name;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @RequestMapping("/show-value")
    @ResponseBody   /*结合返回类型String，直接在页面打印String值*/
    public String showValue(){

        return "show value"+
                "<br>"+
                myName +
                "<br>"+
                city+
                "<br>"+
                areaName+
                "<br>"+
                name+location;
    }
}
