package com.example.springsecurity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 15:01
 * 账户注销处理器
 */
@Component
public class MyLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , Authentication authentication) {
        //todo logout的业务,清除token
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                cookie.setMaxAge(0);
                break;
            }
        }
    }
}
