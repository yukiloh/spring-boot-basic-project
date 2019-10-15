package com.example.demo.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/*自定义realm，在其中重写AuthorizationInfo授权信息 和 AuthenticationInfo认证信息
* shiro的2个核心即是认证与授权*/

@Component
public class UserRealm extends AuthorizingRealm {

    /*授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权");

        /*创建授权信息*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        /*为指定资源添加授权字符串,此处字符串需要与ShiroConfig中的[...]一致*/
        info.addStringPermission("user:update");
        return info;
    }

    /*认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证");

        /*从数据库中获取用户名和密码*/
        String userName = "admin";
        String password = "admin";

        /*这个token是UsernamePasswordToken.login(token)中的token传入的,所以强转类型*/
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        if (!userName.equals(usernamePasswordToken.getUsername())) {
            return null;/*当返回null后shiro的底层会返回UnknownAccountException*/
        }
                            /*3个参数:返回login方法的数据,password(主要参数),realm的名称*/
        return new SimpleAuthenticationInfo("",password,"");
    }


}
