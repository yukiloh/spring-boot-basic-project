package com.example.security.config;

import com.example.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 12:24
 * WebSecurityConfigurerAdapter: 权限管理的核心配置
 * adapter的最简配置,login/logout页面由spring-security提供
 */
//@Configuration        //可以通过开启或关闭来启用adapter
public class SimpleWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 进行配置http相关的数据(拦截请求地址,并进行认证)
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                        //使用spring-security提供的表单登陆页面
                .and()
                .authorizeRequests()            //进行认证相关配置:
                .anyRequest()                   //拦截任何请求
                .authenticated()                //并需要身份认证

        //你可以在源码中看到,WebSecurityConfigurerAdapter下的configure方法中,spring已经为你写上了最简配置
        ;
    }

    /**
     * 配置登陆的数据源,以及密码加密格式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //不使用数据库,直接在内存中配置用户信息(进行匹配)
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}admin")
            .roles("ADMIN")
        ;
    }

    /**
     * 配置一些忽略的路径(静态资源)
     */
    @Override
        public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }

}