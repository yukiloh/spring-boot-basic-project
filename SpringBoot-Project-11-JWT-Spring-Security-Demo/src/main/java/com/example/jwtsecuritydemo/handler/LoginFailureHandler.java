package com.example.jwtsecuritydemo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 * 登陆失败和jwt解析/认证失败都是调用本处理器
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(
	        HttpServletRequest request
            , HttpServletResponse response
            , AuthenticationException exception
    ) throws IOException, ServletException {
	    //返回401未认证
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
	
}
