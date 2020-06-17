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
//@Configuration
public class CustomizeWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    //提供用户的数据源,service内会接入dao
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    //提供权限鉴定的数据源(访问对应路径需要何种角色)
    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    //权限鉴定管理器,决定是否允许访问请求的路径
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    //拒绝访问控制器,当没有权限后会进行拒绝访问的操作,在这里重写业务逻辑
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    //认证失败控制器,当认证失败后会抛出各种异常(AuthenticationException)
    //本案例中选择继续抛出,交给AuthenticationEntryPoint来处理
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    //用于处理认证异常AuthenticationException(用户名密码错误等)
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    //认证成功控制器.在内部编写完成认证后的逻辑
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    //登出成功控制器
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

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
//                .loginPage("/login")                                        //可以自定义登陆页面路径(因为没有设置所以注释)
//                .loginProcessingUrl("/login.do")                            //定义发起登陆的路径
                .usernameParameter("username").passwordParameter("password")
                .failureHandler(myAuthenticationFailureHandler)             //登陆成功时的处理
                .successHandler(myAuthenticationSuccessHandler)             //登陆失败时的处理
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)               //登出成功时的处理
                .permitAll()                                                //允许所有请求(与authorizeRequests相反)

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
                //指定加密方式,并在内部添加前缀,指定加密方式: password("{bcrypt}xxx")
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