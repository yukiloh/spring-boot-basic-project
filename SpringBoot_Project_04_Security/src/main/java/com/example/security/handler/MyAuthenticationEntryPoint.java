package com.example.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/15 0:02
 * 用于处理spring-security抛出的AuthenticationException异常(认证失败)
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , AuthenticationException ex) throws IOException, ServletException {
        //todo 具体异常的处理
        httpServletResponse.setHeader("Content-Type", "text/html; charset=UTF-8");
        System.out.println("AuthenticException 异常抛出:"+ ex);
        httpServletResponse.getWriter().write(ex.getMessage());
    }
}
