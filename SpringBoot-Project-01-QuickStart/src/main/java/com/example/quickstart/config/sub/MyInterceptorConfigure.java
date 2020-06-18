package com.example.quickstart.config.sub;

import com.example.quickstart.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/18 17:32
 * 拦截器的配置类
 * 最终会通过@Import注入到主配置类(MyInterceptorConfigure)中
 */
public class MyInterceptorConfigure implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).
                addPathPatterns("/**").                                         //拦截路径(所有)
                excludePathPatterns("/login","/error","/static/**");            //需要放行路径

        //一个拦截器对应一组拦截/放行地址
        //如果有多个拦截器则需要再通过registry来注册
    }
}
