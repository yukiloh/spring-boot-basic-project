package com.example.adminLET.service;


import com.example.adminLET.domain.UserTable;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UserService {

    UserTable selectOne(String uuid);

    List<UserTable> selectAll();

    PageInfo<UserTable> page(int pageNum, int pageSize, UserTable userTable);
}
