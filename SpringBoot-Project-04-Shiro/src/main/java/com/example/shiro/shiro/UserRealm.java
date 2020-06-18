package com.example.shiro.shiro;

import com.example.shiro.mapper.ILocalUserMapper;
import com.example.shiro.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义realm，在其中重写AuthorizationInfo授权信息 和 AuthenticationInfo认证信息
 * shiro的2个核心即是认证与授权
 */

@Component
public class UserRealm extends AuthorizingRealm {

    /**
     * 授权authz,赋予账号权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("执行授权");

        //创建授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //从认证处返回的principals获取user对象
        User user = (User)principals.getPrimaryPrincipal();

        //为指定资源添加授权字符串,此处字符串需要与ShiroConfig中所设置的一致
        //获取perm
        String userPerm = user.getPerm();       //示例:此处会从user中获取"perm:add"
                                                //@RequiresPermissions({"perm:add"})中的value会与其匹配

        //判断perm是否存在多个("perm:add,perm:update"),
        //因为在本案例中,存在需要add和update2种权限才可以访问的页面
        String[] split = userPerm.split(",");
        for (String s : split) {
            info.addStringPermission(s);
        }

        //获取role,role也可以addRoles来添加多个角色(没有多重角色的设置)
        String role = user.getRole();       //从user中获取"role:admin"
                                            //@RequiresRoles("role:admin")中的value会与其匹配
        info.addRole(role);


        //返回info
        return info;
    }

    @Autowired
    private ILocalUserMapper userMapper;

    /**
     * 认证authc,认证账号
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        System.out.println("当执行subject.login(token)后便会在此处进行账号认证");

        //此处的token是登陆页面中 UsernamePasswordToken.login(token)中传入的token
//        System.out.println(token.getPrincipal());     //从token中获取账号名,一般为String,
//        System.out.println(token.getCredentials());   //从token中获取密码,一般为char[]

        //一般可以用强转的方式来获取传入前的token类型,而不是像↑那样直接获取token.get....
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;

        //获取username,通过数据库进行查找
        String username = usernamePasswordToken.getUsername();
        User user = userMapper.findUserByUsername(username);

        //账号不存在的情况
        if (user == null || user.getUserName()==null) {
            //这里返回null后,shiro会直接返回异常UnknownAccountException(login页面会进行捕获)
            return null;
        }

        //如果账号存在，则返回查询到的password进行比较
        String password = user.getPassword();

        //3个参数:传递至授权的对象|方法,password(主要参数),realm的名称
        return new SimpleAuthenticationInfo(user,password,"");
    }

}
