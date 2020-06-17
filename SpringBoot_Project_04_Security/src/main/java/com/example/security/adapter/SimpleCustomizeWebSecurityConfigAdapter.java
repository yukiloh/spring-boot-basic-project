package com.example.security.adapter;

import com.example.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 12:24
 * WebSecurityConfigurerAdapter: 权限管理的核心配置
 * 对adapter进行一些简单的路径和角色权限配置
 */
@Configuration
public class SimpleCustomizeWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 进行配置http相关的数据(拦截请求地址,并进行认证)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()                    //使用spring-security提供的表单登陆页面

                .and()
                .authorizeRequests()                                //进行认证相关配置
                .antMatchers("/","/index","/test")      //对此类路径,
                .permitAll()                                        //全部放行

                .antMatchers("/user").hasRole("USER")   //指定/user路径需要role:USER
//                .anyRequest().authenticated()                       //①其余的请求则需要认证

                //②如果需要自定义请求,可以进行重写requestMatcher.像下面,对访问hello的路径需要进行认证
                .requestMatchers(
                        (RequestMatcher) request -> "/hello".startsWith(request.getServletPath())
                        //"1".equals(request.getParameter("type"))
                ).authenticated()
    ;


        http.authorizeRequests()                                    //也可以使用多个http
                .antMatchers("/admin").hasRole("ADMIN") //再指定admin
        ;


//                //使用jwt时需要禁用session,设置以下2项(未测试)
//                //详细参考: https://qtdebug.com/html/spring-boot/Security.html
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .csrf().disable()
//                //选择插入在哪个filter前,JwtAuthenticationFilter需要自定义,并继承OncePerRequestFilter
//                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
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