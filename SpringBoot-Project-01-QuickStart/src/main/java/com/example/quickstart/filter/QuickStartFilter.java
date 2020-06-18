package com.example.quickstart.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/18 16:56
 * 通过配置类中的 @Bean 注入到容器,实现对特定路径的过滤
 */
public class QuickStartFilter implements Filter {

    private String filterName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter is init...filter name: "+filterConfig.getFilterName());
        filterName = filterConfig.getFilterName();
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest
            , ServletResponse servletResponse
            , FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println(filterName+": /quickstart is accessed");

        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("filter destroyed...");
    }
}
