package com.example.security.config;

import com.example.security.handler.MyFilterInvocationSecurityMetadataSource;
import com.example.security.handler.*;
import com.example.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 12:24
 * WebSecurityConfigurerAdapter 权限管理的核心配置
 * 进行进阶的自定义配置
 */
@Configuration
//@EnableGlobalMethodSecurity     //代表开启注解,详细: https://www.jianshu.com/p/41b7c3fb00e0
public class CustomizeWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;


    /**
     * 通过定义withObjectPostProcessor方法来实现自定义认证
     * 用户访问访问页面后会先进入adapter
     * 再传给 FilterInvocationSecurityMetadataSource 过滤器数据源,从中获取对应路径的权限
     * 然后传给 AccessDecisionManager 权限决策管理器,进行权限匹配
     * 如果匹配则传给 AccessDeniedHandler,执行登陆成功的操作
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);  //设置过滤器数据源
                        o.setAccessDecisionManager(myAccessDecisionManager);                    //设置权限决策管理器
                        return o;
                    }
                })

                .and()
                .formLogin()
//                .loginPage("/login")                                        //定义登陆页面路径(因为没有设置所以注释)
//                .loginProcessingUrl("/login.do")                            //定义发起登陆的路径
                .usernameParameter("username").passwordParameter("password")
                .failureHandler(new MyAuthenticationFailureHandler())       //登陆成功时的处理
                .successHandler(new MyAuthenticationSuccessHandler())       //登陆失败时的处理
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler(new MyLogoutSuccessHandler())         //登出成功时的处理
                .permitAll()

                .and()
                .exceptionHandling()                                        //异常处理相关
                .accessDeniedHandler(myAccessDeniedHandler)                 //处理AccessDeniedException相关异常
                .authenticationEntryPoint(myAuthenticationEntryPoint)       //处理AuthenticationException
        ;
    }

    /**
     * 配置userDetails的数据源,以及密码加密格式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                //指定加密方式,并在内部指定加密方式: password("{noop}user")
//                .passwordEncoder(new BCryptPasswordEncoder())
        ;
    }

    /**
     * 配置一些忽略的路径(静态资源)
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/ignore");
        web.ignoring().antMatchers("/static/**");
    }

}