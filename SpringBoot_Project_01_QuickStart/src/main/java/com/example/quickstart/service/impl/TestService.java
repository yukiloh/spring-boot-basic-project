package com.example.quickstart.service.impl;

import org.springframework.stereotype.Service;

@Service
//@Component      //@Component与@Service,@Repository本质是一个效果,方便区分而已
public class TestService {

    /**
     * 模拟service层的操作
     */
    public String TestDoSomething(String msg) {
        System.out.println(msg);
        return msg;
    }
}
