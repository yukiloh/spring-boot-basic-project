package com.example.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 15:01
 * 账户注销成功处理器
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(
            HttpServletRequest httpServletRequest
            ,HttpServletResponse httpServletResponse
            ,Authentication authentication
    ) throws IOException, ServletException {
        System.out.println("logout success");
        httpServletResponse.sendRedirect("/");        //登出成功后重定向至logout页面
    }
}
