package com.example.jwtsecuritydemo.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 */
public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	public MyUsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login", "POST"));
	}
	
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(getAuthenticationManager(), "AuthenticationManager must be specified");
		Assert.notNull(getSuccessHandler(), "AuthenticationSuccessHandler must be specified");
		Assert.notNull(getFailureHandler(), "AuthenticationFailureHandler must be specified");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        System.out.println(body);
		String username = null, password = null;
		if(StringUtils.hasText(body)) {
		    JSONObject jsonObj = JSON.parseObject(body);
		    username = jsonObj.getString("username");
            password = jsonObj.getString("password");
            System.out.println(username+password);
        }
		
		if (username == null) 
			username = "";
		if (password == null)
			password = "";
		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}

}
