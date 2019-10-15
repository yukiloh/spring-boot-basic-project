###shiro的核心api

获取subject用户主体，交由securityManger安全管理器去关联realm（shiro连接数据的桥梁）

###shiro的步骤

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


#### 认证 Authentication 
进行用户名密码的认证,认证后可以访问authc下的所有路径




#### 授权 Authorization
进行用户名的授权,授权后可以访问需要perms（permission）、或符合角色roles的路径

    
