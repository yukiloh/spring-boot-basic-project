###shiro的核心api

获取subject用户主体，交由securityManger安全管理器去关联realm（shiro连接数据的桥梁）

###shiro的基础创建步骤

1.编写shiro的配置类ShiroConfig,在其中:
    1.1创建shiroFilterFactoryBean
        new ShiroFilterFactoryBean();
        通过设置shiroFilterFactoryBean完成shiroFilter的自定义
        1.1.1设置安全管理器setSecurityManager
        1.1.2设置过滤规则(map);shiro包含了四个内置的过滤器来实现权限相关拦截：
             authc：所有已登陆用户可访问
             roles：有指定角色的用户可访问，通过[ ]指定具体角色，这里的角色名称与数据库中配置一致
             perms：有指定权限的用户可访问，通过[ ]指定具体权限，这里的权限名称与数据库中配置一致
             anon：所有用户可访问，通常作为指定页面的静态资源时使用
             并可设置拦截后的跳转登陆页面setLoginUrl("/login");     
         1.1.3最后将配置内容传入过滤器
              shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    1.2创建defaultWebSecurityManger，主要用于设置密码加密
        new DefaultWebSecurityManager();
    1.3创建getRealm（需要自定义）
        new UserRealm()
    
2.创建一个自定义的realm，需要继承AuthorizingRealm，并重写认证和授权方法

shiro基础配置完成。



#### 关于认证和授权的
认证后会赋予用户user的权限（最基础的）
之后再进行授权赋予不同等级的权限


##### 认证 Authentication 
进行用户名密码的认证,认证后可以访问authc下的所有路径

controller中获取username&password
通过subject.login(token)将代有username&password的token传入getAuth中
再将token通过强转类型获取其中的username&password，并进行匹配
判断username，错误直接返回null，controller会接收到UnknownAccountException
再判断password，通过new SimpleAuthenticationInfo传入3个参数（最重要的是password）并进行判断
错误密码将会返回IncorrectCredentialsException


##### 授权 Authorization
进行用户名的授权,授权后可以访问需要perms（permission）、或符合角色roles的路径

创建授权信息new SimpleAuthorizationInfo();
通过info.add...为用户(完成认证的user)赋予权限
shiroConfig会最终进行auth&author的校验
满足条件进行放行

    

#### 关于shiro中注解的使用
##### 5个注解
注解优先级:当一个类/方法/变量存在复数个下列注解时,会按照下列注解的顺序依次进行验证(而非开发者进行注解的顺序),返回false则停止

@RequiresRoles
当前Subject需拥有指定的角色
如无则方法不会执行并抛出UnauthorizedException异常

@RequiresPermissions
当前Subject需拥有某些指定权限
如无则方法不会被执行并抛出UnauthorizedException异常

*1:↑为资源权限控制的主要方案(主要注解)
*2:↑注解的value可以为多个,可以通过logical=Logical.OR 或者AND(默认)的方式来使角色/权限为与/或的条件
    例如:@RequiresPermissions(value={"perm:admin","perm:manger"},logical=Logical.OR)  只要权限perm满足其中一个便可放行
    
@RequiresAuthentication
验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时

@RequiresUser
验证用户是否被记忆，user有两种含义：
一种是成功登录的（subject.isAuthenticated() 结果为true）
另外一种是被记忆的（subject.isRemembered()结果为true）

RequiresGuest
验证是否是一个guest的请求
与@RequiresUser完全相反,RequiresUser  == !RequiresGuest,且此时subject.getPrincipal() 结果为null.

验证顺序(由上往下):
RequiresRoles 
RequiresPermissions 
RequiresAuthentication 
RequiresUser 
RequiresGuest

##### 开启shiro注解的步骤
1.ShiroConfig类中添加下列bean:

/*Shiro生命周期处理器*/
@Bean(name = "lifecycleBeanPostProcessor")
public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
}

/*开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
       需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证*/
@Bean
@DependsOn("lifecycleBeanPostProcessor")
public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    creator.setProxyTargetClass(true);
    return creator;
}

/*开启shiro aop注解支持.因为使用代理方式;所以需要开启代码支持;*/
@Bean
public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
            = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
}


2.创建一个异常捕获器ExceptionHandleController
@ControllerAdvice
public class ExceptionHandleController {
    /*未授权错误*/   /*主要对于此方法进行捕获*/
    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception ex) {
        return "redirect:/unauthorized";    /*重定向至未授权友好页面*/
    }
    /*授权错误*/    /*测试过程中没有出现该类型异常*/
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        return "redirect:/login";
    }
}


3.在controller的类/方法上添加相应注解