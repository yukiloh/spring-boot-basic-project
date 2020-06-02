package com.example.adminLET.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * config类,原用于配置拦截器
 */
@Configuration
public class MainConfig implements WebMvcConfigurer {

//    @Autowired
//    private MainInterceptor mainInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(mainInterceptor).addPathPatterns("/**");
//    }
}
