package com.example.demo.shiro;

import com.example.demo.mapper.ILocalUserMapper;
import com.example.demo.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*自定义realm，在其中重写AuthorizationInfo授权信息 和 AuthenticationInfo认证信息
* shiro的2个核心即是认证与授权*/

@Component
public class UserRealm extends AuthorizingRealm {

    /*授权authz*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("执行授权");

        /*创建授权信息*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        /*从认证处返回的principals获取user对象*/
        User user = (User)principals.getPrimaryPrincipal();

        /*为指定资源添加授权字符串,此处字符串需要与ShiroConfig中的[...]一致*/
        /*获取perm*/
        String userPerm = user.getPerm();
        info.addStringPermission(userPerm);

        /*获取role*/
        String role = user.getRole();
        info.addRole(role);

        /*返回info*/
        return info;
    }

    @Autowired
    private ILocalUserMapper userMapper;
    /*认证authc*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        System.out.println("执行认证");

        /*这个token是UsernamePasswordToken.login(token)中的token传入的,所以强转类型*/
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
        /*获取前端传入的username，并查询数据库*/
        String username = usernamePasswordToken.getUsername();
        User user = userMapper.findUserByUsername(username);
        if (user == null || user.getUserName()==null) {
            return null;/*当返回null后shiro的底层会返回UnknownAccountException*/
        }
        /*如果账号存在，则返回查询到的password进行比较；通常业务中需要将密码加盐*/
        String password = user.getPassword();

                    /*3个参数:传递至授权的对象|方法,password(主要参数),realm的名称*/
        return new SimpleAuthenticationInfo(user,password,"");
    }


}
