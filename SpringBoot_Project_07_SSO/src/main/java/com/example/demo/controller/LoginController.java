package com.example.demo.controller;


import com.example.demo.constants.WebConstants;
import com.example.demo.dto.BaseResult;
import com.example.demo.service.LoginService;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*单点登录，返回页面*/
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    /*剩余任务:实现service接口,编写controller*/
    /* 登录逻辑：
     * 判断是否有token，有则获取并从redis中查找，无则进入登录*/

    /**
     * 通过loginService方法，对用户名密码校验
     * 成功则向前端提供user的token，并存入redis
     *
     * @param loginCode 登录账号，可以考虑同时允许用户名/手机号/邮箱地址进行登录
     * @param password  密码
     * @return          JSON数据
     */
    @ResponseBody
    @PostMapping("/login.do")
    public BaseResult login(String loginCode
                            ,String password
//                            ,HttpServletRequest request
//                            ,HttpServletResponse response
                            ){
        String login = loginService.login(loginCode, password);

        /*登陆失败*/
        if (login == null) {
            BaseResult.Error error = new BaseResult.Error("login","用户名或密码错误");
            List<BaseResult.Error> errors = new ArrayList<>();
            errors.add(error);
            return BaseResult.notOk(errors);
        }

        /*登录成功,存入redis*/
        String token = UUID.randomUUID().toString();
        try {
            redisService.put(token, login, WebConstants.QUATER_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        /*在cookie中存放token的值*/
//        CookieUtils.setCookie(request,response,WebConstants.SESSION_TOKEN,token,(int)WebConstants.QUATER_DAY);

        /*提供json数据(带token)给前端，而不是自己去set cookie*/
        return BaseResult.ok(token);
    }




}
