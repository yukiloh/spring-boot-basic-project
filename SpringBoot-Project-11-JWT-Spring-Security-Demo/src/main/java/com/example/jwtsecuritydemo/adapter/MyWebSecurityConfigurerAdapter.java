package com.example.jwtsecuritydemo.adapter;

import com.example.jwtsecuritydemo.configuration.JsonLoginConfigurer;
import com.example.jwtsecuritydemo.configuration.JwtLoginConfigurer;
import com.example.jwtsecuritydemo.filter.OptionsRequestFilter;
import com.example.jwtsecuritydemo.handler.JsonLoginSuccessHandler;
import com.example.jwtsecuritydemo.handler.JwtRefreshSuccessHandler;
import com.example.jwtsecuritydemo.handler.TokenClearLogoutHandler;
import com.example.jwtsecuritydemo.handler.JwtAuthenticationProvider;
import com.example.jwtsecuritydemo.service.JwtUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/18 16:27
 * adapter
 */
@Configuration
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //拦截user路径,并需要USER角色
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()

                .and()
                //禁用csrf
                .csrf().disable()
                //关闭表单登陆
                .formLogin().disable()
                //允许跨域
                .cors()

                .and()
                //response也需要添加跨域支持
                .headers().addHeaderWriter(
                        new StaticHeadersWriter(Arrays.asList(
                            new Header("Access-control-Allow-Origin","*"),
                            new Header("Access-Control-Expose-Headers","Authorization")
                        ))
                )

                .and()
                //添加拦截options的filter(ajax请求会先询问服务器是否支持options)
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)

                //应用登陆的configure,
                .apply(new JsonLoginConfigurer<>())
                .loginSuccessHandler(jsonLoginSuccessHandler())

                .and()
                //应用jwt的filter
                .apply(new JwtLoginConfigurer<>())
                .tokenValidSuccessHandler(jwtRefreshSuccessHandler())
                .permissiveRequestUrls("/logout")

                .and()
                //设置登出
                .logout()
                .addLogoutHandler(tokenClearLogoutHandler())
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())

                .and()
                //设置关闭session(通过session创建策略)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
        ;
    }

    /**
     * 配置provider
     * 之前的案例中,通过配置userDetails的数据源来进行认证,现在需要自定义认证方法因此使用provider
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(jwtAuthenticationProvider());
    }

    /**
     * 后面都是一些需要初始化的配置
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("jwtAuthenticationProvider")
    protected AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtUserService());
    }

    @Bean("daoAuthenticationProvider")
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
        //这里会默认使用BCryptPasswordEncoder比对加密后的密码,创建用户createUser时也需要使用相同的加密方式
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService());
        return daoProvider;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new JwtUserService();
    }

    @Bean("jwtUserService")
    protected JwtUserService jwtUserService() {
        return new JwtUserService();
    }

    @Bean
    protected JsonLoginSuccessHandler jsonLoginSuccessHandler() {
        return new JsonLoginSuccessHandler(jwtUserService());
    }

    @Bean
    protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
        return new JwtRefreshSuccessHandler(jwtUserService());
    }

    @Bean
    protected TokenClearLogoutHandler tokenClearLogoutHandler() {
        return new TokenClearLogoutHandler(jwtUserService());
    }

    //因为开启了跨域支持cors(),spring会从CorsConfigurationSource中获取相关设置
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //使用singletonList来替换Arrays.asList,减少内存分配
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD", "OPTION"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
