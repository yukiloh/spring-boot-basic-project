package com.example.spring_security_oauth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/*认证的配置*/
@Configuration
@EnableWebSecurity          /*开启spring security*/
@EnableGlobalMethodSecurity /*开启所有拦截方法请求*/
/*另需要继承Security的适配器WebSecurityConfigurerAdapter*/
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /*密码加密方式*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        /*进行密码适配，加密可能存在MD5，SHA256多种，该方法会对密码进行类型适配*/
        return new BCryptPasswordEncoder();
    }

    /*配置账号*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*在内存中设置认证,创建2个角色*/
        auth.inMemoryAuthentication()
        /*设置用户名、密码、角色认证（可多个），密码必须通过↑passwordEncoder进行加密*/
        .withUser("user").password(passwordEncoder().encode("111111")).roles("USER")
        .and()
        .withUser("admin").password(passwordEncoder().encode("222222")).roles("ADMIN");
    }
}
