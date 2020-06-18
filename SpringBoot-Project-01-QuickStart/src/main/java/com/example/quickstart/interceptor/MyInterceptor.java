package com.example.quickstart.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/18 17:34
 * 拦截器
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 在访问对应路径前进行拦截,可以获取request和response,对内容进行修改
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request
            , HttpServletResponse response
            , Object handler
    ) throws Exception {
        //
        System.out.println("MyInterceptor: "+request.getRequestURI()+" is accessed");
        return true;
    }

    /**
     * 在访问路径后进行拦截
     * 在这里会获取 modelAndView ,可以用来渲染视图
     */
    @Override
    public void postHandle(
            HttpServletRequest request
            , HttpServletResponse response
            , Object handler, ModelAndView modelAndView
    ) throws Exception {

    }

    /**
     * servlet进行视图渲染后,多用于后续清理
     */
    @Override
    public void afterCompletion(HttpServletRequest request
            , HttpServletResponse response
            , Object handler
            , Exception ex
    ) throws Exception {

    }
}
