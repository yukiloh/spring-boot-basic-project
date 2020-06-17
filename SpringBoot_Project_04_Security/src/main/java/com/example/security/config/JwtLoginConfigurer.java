package com.example.security.config;

import com.example.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/17 18:39
 */
public class JwtLoginConfigurer<T extends JwtLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    @Autowired
    private JwtAuthenticationFilter authFilter;

    @Override
    public void configure(B http) throws Exception {
//        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
//        authFilter.setAuthenticationFailureHandler(new HttpStatusLoginFailureHandler());
        //将filter放到logoutFilter之前
        JwtAuthenticationFilter filter = postProcess(authFilter);
        http.addFilterBefore(filter, LogoutFilter.class);
    }
    //设置匿名用户可访问url
    public JwtLoginConfigurer<T, B> permissiveRequestUrls(String ... urls){
//        authFilter.setPermissiveUrl(urls);
        return this;
    }

    public JwtLoginConfigurer<T, B> tokenValidSuccessHandler(AuthenticationSuccessHandler successHandler){
//        authFilter.setAuthenticationSuccessHandler(successHandler);
        return this;
    }
}
