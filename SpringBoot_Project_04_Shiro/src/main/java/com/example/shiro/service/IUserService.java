package com.example.shiro.service;

import com.example.shiro.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    User findUserByName(String username);

}
