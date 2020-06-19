package com.example.jwtsecuritydemo.handler;

import com.example.jwtsecuritydemo.service.JwtUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过json登陆成功的处理器
 */
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUserService jwtUserService;
	
	public JsonLoginSuccessHandler(JwtUserService jwtUserService) {
		this.jwtUserService = jwtUserService;
    }

	@Override
	public void onAuthenticationSuccess(
	        HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication
    ) throws IOException, ServletException {
	    //获取jwt
		String token =
                jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());

		//将jwt设置到header中
		response.setHeader("Authorization", token);
	}
	
}
