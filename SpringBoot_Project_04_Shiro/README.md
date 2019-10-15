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
    1.2创建defaultWebSecurityManger
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

    
