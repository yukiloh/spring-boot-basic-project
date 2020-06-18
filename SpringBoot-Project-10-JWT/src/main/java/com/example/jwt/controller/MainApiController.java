package com.example.jwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.jwt.annotation.UserLoginToken;
import com.example.jwt.entity.User;
import com.example.jwt.service.JwtService;
import com.example.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * controller,访问地址: localhost:8888/api/...
 */
@RestController
@RequestMapping("api")
public class MainApiController {

    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    //登录
    @PostMapping("/login")
    public Object login( User user){
        JSONObject jsonObject = new JSONObject();
        User originalUser = userService.findByUsername(user);
        if(originalUser ==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!originalUser.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                //将从数据库中获取的用户信息,交给jwt-java进行转换,获取token
                String token = jwtService.getToken(originalUser);
                jsonObject.put("token", token);
                jsonObject.put("user", originalUser);
                return jsonObject;
            }
        }
    }
    
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }


}
