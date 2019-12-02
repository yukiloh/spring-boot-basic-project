package com.example.demo.service;


import com.example.demo.domain.TbSysUser;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    /*注册*/
    void register(TbSysUser tbSysUser);

    /*允许注册*/
    Boolean AllowToRegistered(String loginCode);

    /**
     *  登录
     * @param loginCode 登陆账号
     * @param plantPassword 明文密码
     * @return
     */
    TbSysUser login(String loginCode, String plantPassword);


    TbSysUser loginByToken(String token);
}
