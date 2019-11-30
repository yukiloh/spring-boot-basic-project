package com.example.demo.controller;


import com.example.demo.constants.WebConstants;
import com.example.demo.domain.TbSysUser;
import com.example.demo.service.LoginService;
import com.example.demo.service.redisUtils.RedisService;
import com.example.demo.utils.CookieUtils;
import com.example.demo.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/*单点登录，返回页面*/
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;
/*剩余任务:实现service接口,编写controller*/

    /**
     * 登录逻辑：
     * 判断是否有token，有则获取并去redis中查找，无则进入登录
     *
     * 通过loginService方法，对用户名密码校验
     * 成功则返回token，并存入redis，value对应user信息
     *
     * @param loginCode 登录账号，可以考虑同时允许用户名/手机号/邮箱地址进行登录
     * @param password  密码
     * @return          JSON数据
     */
    @ResponseBody
    @PostMapping("/login.do")
    public String login(String loginCode,
                        String password,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model ){
        TbSysUser tbSysUser = loginService.login(loginCode, password);
        String jsonResult = "bad";


        /*登陆失败*/    /*jsonResult.bad.noSuchUserOrPassword*/
        if (tbSysUser == null)  return jsonResult;

        /*登录成功*/
        String token = UUID.randomUUID().toString();

        /*存入redis,如果存入失败,则进行重试,超过重试次数后返回服务器错误信息*/
        Boolean isSaved = false;
        int count = 0,max = 10;
        while (!isSaved || count > max){
            try {
                isSaved = redisService.put(token, MapperUtils.obj2json(tbSysUser), 24 * 3600);  /*可以抽取至constants*/
                if (!isSaved) count ++;
                /*jsonResult.bad.serviceError*/
                if (count > max) return jsonResult;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*在cookie中存放token的值*/
        CookieUtils.setCookie(request,response,WebConstants.SESSION_TOKEN,token,24 * 3600);

        /*jsonResult.ok*/
        return jsonResult;
    }




}
