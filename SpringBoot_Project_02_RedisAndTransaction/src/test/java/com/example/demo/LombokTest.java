package com.example.demo;

import com.example.demo.model.UserWithLombok;
import org.junit.Test;

public class LombokTest {

    /*测试lombok，未开启插件会红*/
    @Test
    public void testForLombok(){
        UserWithLombok user = new UserWithLombok();
//        user.setUsername("aaa");
//        System.out.println(user.getUsername());
    }

}
