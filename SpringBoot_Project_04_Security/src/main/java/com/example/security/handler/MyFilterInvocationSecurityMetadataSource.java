package com.example.security.handler;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 13:55
 */

import com.example.security.exception.MyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 过滤器数据源
 * Spring Security是通过SecurityMetadataSource来加载访问时所需要的具体权限.Metadata:元数据
 * 它的主要责任: 当访问一个url时，返回这个url所需要的访问权限
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final String ROLE_PREFIX = "ROLE_";

    /**
     * 主要对getAttributes方法进行重写
     * 返回本次访问需要的权限(可以是多个)
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();     //获取所访问的url,会附带斜杠,例如:/user
        System.out.println("request url is: "+requestUrl);

        //todo 查询数据库,获取角色相应权限.这里进行简单模拟
        return getRoles(requestUrl);
    }

    /**
     * 如果是多个权限资源列表则重写本方法,返回null代表不需要校验
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 方法返回类对象是否支持校验(?)
     * web项目一般使用FilterInvocation来判断，或者直接返回true
     */
    @Override
    public boolean supports(Class<?> aClass) {
        //参数与FilterInvocation是否使用同一接口/父类
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

    private Collection<ConfigAttribute> getRoles(String url) {
        if ("/user".equals(url)) {              //访问user页面
            return SecurityConfig.createList(ROLE_PREFIX+"USER");
        }else if ("/admin".equals(url)) {       //访问admin页面
            return SecurityConfig.createList(ROLE_PREFIX+"ADMIN");
        }else if ("/".equals(url) || "/index".equals(url)) {        //设置不进行拦截的页面
            return null;
        }
        throw new AccessDeniedException("没有找到与路径相匹配的权限");
    }
}
