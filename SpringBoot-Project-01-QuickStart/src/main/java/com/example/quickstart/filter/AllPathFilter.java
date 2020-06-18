package com.example.quickstart.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/18 16:56
 * 通过添加 @Component ,对所有路径都进行拦截
 * 也可以在内部判断哪些路径需要进行过滤
 */
@Component
@Order(1)           //重要级别,数字越小越高
public class AllPathFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //filter会在servlet创建时初始化
        System.out.println("filter is init...filter name: "+filterConfig.getFilterName());
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest
            , ServletResponse servletResponse
            , FilterChain filterChain
    ) throws IOException, ServletException {
        //filter可以获取每次访问的request和response
        //这里演示获取所访问的地址,因此将ServletRequest转换为HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //打印所访问的地址
        System.out.println("access uri: "+request.getRequestURI());

        //最后交出过滤链,即放行.如果选择不放行则不交
        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("filter destroyed...");
    }
}
