package com.example.jwtsecuritydemo.configuration;

import com.example.jwtsecuritydemo.filter.MyUsernamePasswordAuthenticationFilter;
import com.example.jwtsecuritydemo.handler.LoginFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

/**
 * json登陆的配置类,主要配置了使用json登陆filter
 * 会拦截访问 /login 的请求
 */
public class JsonLoginConfigurer<T extends JsonLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

	private MyUsernamePasswordAuthenticationFilter authenticationFilter;

	//通过构造器注入filter
	public JsonLoginConfigurer() {
		this.authenticationFilter = new MyUsernamePasswordAuthenticationFilter();
	}
	
	@Override
	public void configure(B http) throws Exception {
	    //(从公共类中)获取AuthenticationManager
		authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

		//设置认证失败处理器
		authenticationFilter.setAuthenticationFailureHandler(new LoginFailureHandler());

		//指定策略,不将认证后的SecurityContext放入session中
		authenticationFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

		//初始化filter,内部会调用ObjectPostProcessor来进行初始化
		MyUsernamePasswordAuthenticationFilter filter = postProcess(this.authenticationFilter);

        //指定filter的位置
        http.addFilterAfter(filter, LogoutFilter.class);
	}

	//设置登陆成功后的处理器
	public JsonLoginConfigurer<T,B> loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler){
		authenticationFilter.setAuthenticationSuccessHandler(authSuccessHandler);
		return this;
	}

}
