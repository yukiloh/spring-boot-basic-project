package com.example.springsecurity.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 13:19
 * 用于处理spring-security抛出的AccessDeniedException异常(访问失败,没有权限)
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest
            ,HttpServletResponse httpServletResponse
            ,AccessDeniedException ex
    ) throws IOException, ServletException {
        //todo 处理访问被拒绝后的操作
        System.out.println("AccessDeniedException 异常抛出:"+ ex);
        httpServletResponse.setHeader("Content-Type", "text/html; charset=UTF-8");
        httpServletResponse.getWriter().write(ex.getMessage());

    }
}
