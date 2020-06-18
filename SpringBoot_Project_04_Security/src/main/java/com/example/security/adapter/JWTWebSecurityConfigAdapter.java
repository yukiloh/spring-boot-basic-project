package com.example.security.adapter;

import com.example.security.config.JsonLoginConfigurer;
import com.example.security.config.JwtLoginConfigurer;
import com.example.security.filter.OptionsRequestFilter;
import com.example.security.handler.JsonLoginSuccessHandler;
import com.example.security.handler.MyAuthenticationSuccessHandler;
import com.example.security.handler.MyLogoutHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 12:24
 * WebSecurityConfigurerAdapter: 权限管理的核心配置
 * 通过JWT实现登陆
 */
@Configuration
public class JWTWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {


    /**
     * 进行配置http相关的数据(拦截请求地址,并进行认证)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/index","/test")
                .permitAll()
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER")

                .and()

                .csrf().disable()                            //禁用CSR(因为不使用session)
                //顺便一提,常见的防止CSRF一般有2种办法,修改操作全post,提交时带hidden_token

                .sessionManagement().disable()              //禁用session
                .formLogin().disable()                      //禁用form登录
                .cors()                                     //支持跨域

                .and()                                      //添加header设置,允许跨域
                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                new Header("Access-control-Allow-Origin","*"),
                new Header("Access-Control-Expose-Headers","Authorization"))))

                .and()
                //拦截OPTION请求,插在cors前
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)

                //添加通过json登录的filter
                .apply(new JsonLoginConfigurer<>())
                .loginSuccessHandler(new JsonLoginSuccessHandler())

                //添加检查token的filter
                .and()
                .apply(new JwtLoginConfigurer<>())
                .tokenValidSuccessHandler(new MyAuthenticationSuccessHandler())
                .permissiveRequestUrls("/logout")

                .and()
                .logout()                                                               //使用默认的logoutFilter
                .addLogoutHandler(new MyLogoutHandler())                                //logout时清除token
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())    //logout成功后返回200
        ;
    }

    /**
     * 配置登陆的数据源,以及密码加密格式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //todo 测试专用.不使用数据库,直接在内存中配置用户信息(进行匹配)
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}admin")
            .roles("ADMIN","USER")

            //再创建一个user用户
            .and().withUser("user").password("user").roles("USER")
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