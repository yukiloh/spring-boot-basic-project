package com.example.jwtsecuritydemo.handler;

import com.example.jwtsecuritydemo.service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ?
 */
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUserService jwtUserService;
	
	public JsonLoginSuccessHandler(JwtUserService jwtUserService) {
		this.jwtUserService = jwtUserService;
    }

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
		String token = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
		response.setHeader("Authorization", token);
	}
	
}
