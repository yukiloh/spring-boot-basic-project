package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    User findUserByName(String username);

}
