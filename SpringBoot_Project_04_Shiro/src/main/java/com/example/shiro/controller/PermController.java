package com.example.shiro.controller;

import org.apache.shiro.authz.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用于用于鉴权的controller
 */

@Controller
public class PermController {

    /**
     * @RequiresRoles           依赖的相应角色
     * @RequiresPermissions     依赖的相应权限
     * @RequiresAuthentication  需要登录后
     * @RequiresUser            允许所有登录的用户访问
     * @RequiresGuest           允许未登录的用户访问,登录的反而不能访问,会抛出授权错误异常AuthorizationException
     */

    /**
     * 允许拥有添加user的权限访问
     */
    @RequiresPermissions({"perm:add"})
    @GetMapping("/user/add")
    public String addUser() {
        return "/user/add";
    }

    /**
     * 允许拥有更新user的权限访问
     */
    @RequiresPermissions({"perm:update"})
    @GetMapping("/user/update")
    public String updateUser() {
        return "/user/update";
    }

    /**
     * 必须拥有update和add权限才可以访问,通过logical可以设置or或者and
     */
    @RequiresPermissions(value = {"perm:update","perm:add"},logical = Logical.AND)
    @GetMapping("/user/bothPerm")
    public String bothPerm() {
        return "/user/bothPerm";
    }

    /**
     * 允许角色为admin的用户访问
     */
    @RequiresRoles("role:admin")
    @GetMapping("/user/admin")
    public String forRoleAdmin() {
        return "/user/admin";
    }

    /**
     * 只允许游客访问.已经登陆了怎么会忘记密码呢
     */
    @RequiresGuest
    @GetMapping("/user/forgetPassword")
    public String forgetPassword() {
        return "/user/forgetPassword";
    }


    /**
     * 登录成功页面
     */
    @GetMapping("/user/success")
    public String success() {
        return "/user/success";
    }

    /**
     * 未授权页面
     */
    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "/unauthorized";
    }


    /**
     * 授权错误页面
     */
    @GetMapping("/errorAuthorization")
    public String errorAuthorization() {
        return "/errorAuthorization";
    }


}
