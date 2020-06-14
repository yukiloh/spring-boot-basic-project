package com.example.security.service;

import com.example.security.handler.MyAccessDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 13:31
 * 用户信息处理,为构建Authentication对象提供必须的信息.可以创建多个
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //todo 读取数据库.这里进行模拟

        if (username.equals("user")) {
            return User
//                .withDefaultPasswordEncoder()             //不安全的方式,只适合简单的app,已被弃用
                    .withUsername("user")                      //定义用户名
                    /**
                     * 定义密码,需要指定密码策略{xxx}
                     * 可以选择{noop}或者{bcrypt}
                     * 不设置会抛错: There is no PasswordEncoder mapped for the id "null"
                     */
                    .password("{noop}user")
                    .roles("USER")                             //定义角色
                    .build();
        }else if (username.equals("admin")) {
            return User
                    .withUsername("admin")                      //定义用户名
                    .password("{noop}admin")
                    .roles("ADMIN","USER")                             //定义角色,spring会自动在前面添加"ROLE_"的前缀
                    .build();
        }

        System.out.println("用户名不存在");
        throw new UsernameNotFoundException("用户名不存在");
    }
}
