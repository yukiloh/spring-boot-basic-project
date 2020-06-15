package com.example.security.handler;

import com.example.security.exception.MyException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 14:53
 * 认证失败处理器
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest httpServletRequest
            ,HttpServletResponse httpServletResponse
            ,AuthenticationException e
    ) throws IOException, ServletException {
        //todo 实际开发中,可以对每个异常进行不同的操作
        if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
            throw new UsernameNotFoundException("账户名未找到或密码错误!");
        } else if (e instanceof LockedException) {
            throw new LockedException("账号被锁定!");
        } else if (e instanceof CredentialsExpiredException) {
            throw new CredentialsExpiredException("密码过期!");
        } else if (e instanceof AccountExpiredException) {
            throw new AccountExpiredException("账户过期!");
        } else if (e instanceof DisabledException) {
            throw new DisabledException("账户被禁用!");
        } else {
            throw new MyException("登陆失败!");
        }
    }
}
