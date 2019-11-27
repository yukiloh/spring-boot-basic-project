package com.example.spring_security_oauth.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/*授权的配置*/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*在内存中进行模拟*/
        clients.inMemory()
        .withClient("client")
        /*必须加密*/
        .secret(passwordEncoder.encode("secret"))
        /*授权类型*/
        .authorizedGrantTypes("authorization_code")
        /*授权范围*/
        .scopes("app")
        /*回调地址*/
        .redirectUris("https://cn.bing.com/?")
        ;
    }
}
