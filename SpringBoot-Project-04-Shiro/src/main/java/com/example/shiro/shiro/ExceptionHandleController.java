package com.example.shiro.shiro;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常捕获
 */

@ControllerAdvice
public class ExceptionHandleController {

    //未授权跳转
    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception ex) {
        System.out.println("没有权限!");
        return "redirect:/unauthorized";
    }

    //授权错误.比如只允许guest访问的页面,user进行了访问.本案例中为forget password页面
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        System.out.println("授权错误!");
        return "redirect:/errorAuthorization";
    }

}