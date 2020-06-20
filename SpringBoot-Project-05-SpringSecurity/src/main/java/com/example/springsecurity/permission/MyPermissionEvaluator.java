package com.example.springsecurity.permission;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/20 21:19
 * 为注解@PreAuthorize提供hasPermission()中,权限鉴定的功能
 * 需要继承PermissionEvaluator,然后注入spring容器
 */
@Component
public class MyPermissionEvaluator implements PermissionEvaluator{

    /**
     *
     * 参数中,authentication会在使用SpEl表达式 hasPermission() 时自动注入
     * 而o和o1可以自定义,这里约定俗成让o为target,o1为permission
     * @param authentication 认证器
     * @param o     target     所访问路径
     * @param o1    permission 所需要的权限
     * @return true or false
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {

        //防止SpEl写入时是null
        Assert.notNull(o,"in SpEl : hasPermission(target,permission) target is null");
        Assert.notNull(o1,"in SpEl : hasPermission(target,permission) permission is null");

        //最终返回结果
        boolean result = false;

        UserDetails user = (UserDetails) authentication.getPrincipal();
        // todo 根据获取到的user信息,调用service去数据库查询user相关的权限
        // 这里直接模拟获取permission结果
        String permission = "";
        if ("user".equals(user.getUsername())) permission = "PERM_USER";

        //如果target是user,那么比对数据库中获取的permission和传入的参数o1(permission)是否一致
        if (o.equals("user")) result = permission.equals(o1);

        return result;
    }

    /**
     * 对于4个参数的,多了一个序列化接口
     * 可能用于鉴定实体类,没仔细研究.本案例不演示
     * @param authentication
     * @param serializable      需要实现serializable的对象
     * @param s                 targetType,所要匹配的对象类型
     * @param o                 permission,所要鉴定的权限
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
