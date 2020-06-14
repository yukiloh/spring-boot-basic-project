package com.example.security.handler;

import com.example.security.exception.MyException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 14:21
 * 自定义权限决策管理器
 * 在过滤器数据源(FilterInvocationSecurityMetadataSource)中获取了请求地址所对应的权限
 * 因此在此对用户所拥有的权限与路径需要的权限进行鉴定
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 比较当前用户的权限与url的权限,决定是否放行
     * authentication:      包含了当前的用户信息(即UserDetailsService时获取的用户对象)
     * object:              FilterInvocation对象,可获取的例如request的web资源
     * configAttributes:    本次访问所需的权限,即FilterInvocationSecurityMetadataSource中查询到的权限(或列表)
     **/
    @Override
    public void decide(Authentication auth, Object object, Collection<ConfigAttribute> configAttributes) {
        if (auth == null) {
            throw new AccessDeniedException("当前访问没有权限");
        }

        //直接放行.因为   在MetadataSource已设置,如果是非拦截路径则返回null
        if (configAttributes==null){
            return;
        }

        for (ConfigAttribute configAttribute : configAttributes) {
            //当前请求需要的权限
            String requestRole = configAttribute.getAttribute();

            //当前用户所具有的权限,默认会添加前缀"ROLE_"
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(requestRole)) {
                    return;
                }
            }
        }

        //遍历后依然没有获取权限则抛出异常
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
