package com.example.easymybatis;

import com.example.easymybatis.domain.UserTable;
import com.example.easymybatis.mapper.UserTableMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

@SpringBootTest
class EasyMybatisApplicationTests {

    @Autowired
    private UserTableMapper userTableMapper;    //此处idea警告红线,无视即可

    /**
     * 简单的查询案例
     */
    @Test
    void contextLoads() {
        //查询所有
        List<UserTable> userTables = userTableMapper.selectAll();

        //打印结果
        if (userTables!=null) {
            userTables.forEach(user -> System.out.println(user));
        }
    }

}
