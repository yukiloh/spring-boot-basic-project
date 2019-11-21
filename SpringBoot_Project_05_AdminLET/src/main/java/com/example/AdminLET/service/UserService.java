package com.example.AdminLET.service;


import com.example.AdminLET.domain.UserTable;


public interface UserService {

    UserTable selectOne(String uuid);


}
