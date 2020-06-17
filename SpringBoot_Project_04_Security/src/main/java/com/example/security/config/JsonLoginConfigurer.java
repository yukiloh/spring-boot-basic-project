package com.example.security.config;

import com.example.security.filter.MyUsernamePasswordAuthenticationFilter;
import com.example.security.handler.MyAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.stereotype.Component;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/17 18:27
 */
public class JsonLoginConfigurer<T extends JsonLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    private MyUsernamePasswordAuthenticationFilter authFilter = new MyUsernamePasswordAuthenticationFilter();

    @Override
    public void configure(B http) throws Exception {
        //让filter获取AuthenticationManager中的信息
        //AuthenticationManager类似于shiro的SecurityManager,可以获取当前用户的信息
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        //认证失败时
        authFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());

        //设置策略,不将认证后的context放入session
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());


        //创建认证的filter,并插入指定位置
        MyUsernamePasswordAuthenticationFilter filter = postProcess(authFilter);
        http.addFilterAfter(filter, LogoutFilter.class);
    }

    //设置成功的Handler，这个handler定义成Bean，所以从外面set进来
    public JsonLoginConfigurer<T,B> loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler){
        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        return this;
    }

}
