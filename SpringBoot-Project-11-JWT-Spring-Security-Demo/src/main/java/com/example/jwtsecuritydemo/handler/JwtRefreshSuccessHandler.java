package com.example.jwtsecuritydemo.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwtsecuritydemo.configuration.JwtAuthenticationToken;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * ?
 */
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {
	
	private static final int tokenRefreshInterval = 300;  //刷新间隔5分钟

    private final JwtUserService jwtUserService;
	
	public JwtRefreshSuccessHandler(JwtUserService jwtUserService) {
		this.jwtUserService = jwtUserService;
    }

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
		DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();
		boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
		if(shouldRefresh) {
            String newToken = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
            response.setHeader("Authorization", newToken);
        }	
	}
	
	protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }

}
