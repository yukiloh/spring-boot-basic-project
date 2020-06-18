package com.example.springsecurity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 13:06
 * 认证成功处理器.可以添加一些认证成功后的业务
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest
            ,HttpServletResponse httpServletResponse
            ,Authentication authentication
    ) throws IOException, ServletException {
        System.out.println("login success");

        //打印查看用户名密码
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        System.out.println(username+"  "+password);

        //重定向至首页
        httpServletResponse.sendRedirect("/");
    }
}
