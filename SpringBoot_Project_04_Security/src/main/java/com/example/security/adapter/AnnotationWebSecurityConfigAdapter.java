package com.example.security.adapter;

import com.example.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 12:24
 * WebSecurityConfigurerAdapter: 权限管理的核心配置
 * 通过注解@EnableGlobalMethodSecurity,进行权限的限制
 */
//@Configuration
//@EnableGlobalMethodSecurity(        //启用的注解类型可以开启多个,但所应用的方法只有1个会生效
//        securedEnabled = true       //启用@Secured
//        ,prePostEnabled = true      //启用前置注解,@PreAuthorize,@PostAuthorize等
//        ,jsr250Enabled = true       //启用JSR-250相关的注解,@RolesAllowed等
//)
public class AnnotationWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 进行配置http相关的数据(拦截请求地址,并进行认证)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()                    //使用spring-security提供的表单登陆页面

                //需要配置一些基础的认证拦截,否则没有权限时不会跳转至login
                .and()
                .authorizeRequests()
                .antMatchers("/","/index","/test")
                .permitAll()

                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
        ;
    }

    /**
     * 配置userDetails的数据源,以及密码加密格式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(myUserDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder())   //也可以设置加密方式(BCrypt)
        ;
    }

    /**
     * 配置一些忽略的路径(通常配置静态资源)
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }

}