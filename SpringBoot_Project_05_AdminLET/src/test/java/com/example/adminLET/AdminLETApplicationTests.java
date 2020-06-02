package com.example.adminLET;

import com.example.adminLET.domain.UserTable;
import com.example.adminLET.service.UserService;
import com.example.adminLET.utils.MapperUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan("com.example.adminLET.mapper")
@SpringBootTest
class AdminLETApplicationTests {


    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        List<UserTable> userTables = userService.selectAll();
        try {
            String string = MapperUtils.obj2json(userTables);
            System.out.println(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
