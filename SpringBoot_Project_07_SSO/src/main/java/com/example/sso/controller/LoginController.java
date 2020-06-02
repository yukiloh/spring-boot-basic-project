package com.example.sso.controller;


import com.example.sso.constants.WebConstants;
import com.example.sso.domain.TbSysUser;
import com.example.sso.dto.BaseResult;
import com.example.sso.service.LoginService;
import com.example.sso.service.RedisService;
import com.example.sso.utils.CookieUtils;
import com.example.sso.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                            , String password
                            , @CookieValue(required = false) String token
                            , HttpServletRequest request
                            , HttpServletResponse response
                            ){
        /*有token*/
        if (token != null) {
            TbSysUser tbSysUser = loginService.loginByToken(token);
            if (tbSysUser != null) return BaseResult.ok(tbSysUser);
            else CookieUtils.deleteCookie(request,response,"token");
        }

        /*无token,通过用户名密码登录*/
        TbSysUser tbSysUser = loginService.login(loginCode, password);
        /*登陆失败*/
        if (tbSysUser == null) {
            BaseResult.Error error = new BaseResult.Error("login","用户名或密码错误");
            List<BaseResult.Error> errors = new ArrayList<>();
            errors.add(error);
            return BaseResult.notOk(errors);
        }

        /*登录成功,在cookie中存放token的值,并存入token:loginCode*/
        token = UUID.randomUUID().toString();
        try {
            redisService.put(token, MapperUtils.obj2json(tbSysUser), WebConstants.QUATER_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*设置cookie*/
        CookieUtils.setCookie(request,response, WebConstants.SESSION_TOKEN,token,(int)WebConstants.QUATER_DAY);
        /*提供json数据(带token)给前端，而不是自己去set cookie*/
        return BaseResult.ok(tbSysUser);
    }

}
