package com.example.quickstart.config;

import com.example.quickstart.config.sub.MyInterceptorConfigure;
import com.example.quickstart.config.sub.MyProperties;
import com.example.quickstart.filter.QuickStartFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * 配置类config,可以统合其他多个config
 */
@Configuration
@EnableConfigurationProperties({MyProperties.class})      //启用ConfigurationProperties,value可以是数组
@Import(MyInterceptorConfigure.class)                       //添加拦截器的配置类
public class MyConfiguration {

    /**
     * 注入QuickStartFilter,让servlet对 /quickstart 路径进行拦截
     */
    @Bean
    @SuppressWarnings("unchecked")      //压制unchecked警告
    public FilterRegistrationBean myFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new QuickStartFilter());
        registration.addUrlPatterns("/quickstart");
        return registration;
    }


}
