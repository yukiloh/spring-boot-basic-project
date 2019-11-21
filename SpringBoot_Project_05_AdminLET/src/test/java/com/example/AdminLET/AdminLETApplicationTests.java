package com.example.AdminLET;

import com.example.AdminLET.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminLETApplicationTests {


    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        userService.selectOne("1919");

    }



}
