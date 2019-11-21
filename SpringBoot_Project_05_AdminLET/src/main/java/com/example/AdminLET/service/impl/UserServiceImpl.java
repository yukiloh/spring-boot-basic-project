package com.example.AdminLET.service.impl;

import com.example.AdminLET.domain.UserTable;
import com.example.AdminLET.mapper.UserTableMapper;
import com.example.AdminLET.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.spring.annotation.MapperScan;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserTableMapper userTableMapper;

    public UserTable selectOne(String uuid){
        UserTable userTable = new UserTable();
        userTable.setUuid(uuid);
        UserTable obj = userTableMapper.selectOne(userTable);
        System.out.println(obj.getAccount());

        return obj;
    }
}
