package com.example.jwtsecuritydemo.handler;

import com.example.jwtsecuritydemo.service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ?
 */
public class TokenClearLogoutHandler implements LogoutHandler {

    private final JwtUserService jwtUserService;

    public TokenClearLogoutHandler(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }

    @Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		clearToken(authentication);
	}
	
	protected void clearToken(Authentication authentication) {
		if(authentication == null)
			return;
		UserDetails user = (UserDetails)authentication.getPrincipal();
		if(user!=null && user.getUsername()!=null)
		    jwtUserService.deleteUserLoginInfo(user.getUsername());
	}

}
