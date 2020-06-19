package com.example.jwtsecuritydemo.configuration;

import com.example.jwtsecuritydemo.filter.JwtAuthenticationFilter;
import com.example.jwtsecuritydemo.handler.LoginFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * jwt filter的配置类
 * 这里的代码和 JsonLoginConfigurer 中的类似,如果不明白可以看那边
 */
public class JwtLoginConfigurer<T extends JwtLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    
	private JwtAuthenticationFilter authFilter;
	
	public JwtLoginConfigurer() {
		this.authFilter = new JwtAuthenticationFilter();
	}
	
	@Override
	public void configure(B http) throws Exception {
		authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationFailureHandler(new LoginFailureHandler());

		JwtAuthenticationFilter filter = postProcess(authFilter);

		//插在logoutFilter前
		http.addFilterBefore(filter, LogoutFilter.class);
	}
	
	public JwtLoginConfigurer<T, B> permissiveRequestUrls(String ... urls){
	    //设置匿名用户可访问的路径
		authFilter.setPermissiveUrl(urls);
		return this;
	}
	
	public JwtLoginConfigurer<T, B> tokenValidSuccessHandler(AuthenticationSuccessHandler successHandler){
		authFilter.setAuthenticationSuccessHandler(successHandler);
		return this;
	}
	
}
