package com.example.shiro.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //spring的知识:关于bean的作用,使方法返回的对象交由spring管理（供其他方法使用）

    //1.1创建shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //为bean工厂设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //创建shiro定义过滤器的map集合
        Map<String, String> filterChainDefinitionMap = new HashMap<>();

        //shiro通过下面的内置的过滤器,实现权限相关拦截：
        //authc：所有已登陆用户可访问
        //roles：有指定角色的用户可访问，通过[ ]指定具体角色，这里的角色名称与数据库中配置一致
        //perms：有指定权限的用户可访问，通过[ ]指定具体权限，这里的权限名称与数据库中配置一致
        //anon：所有用户可访问，通常作为指定页面的静态资源时使用

        //设置perms过滤器,因为已使用了注解进行配置,下方注释
        //个人也比较推荐通过注解来设置权限,本案例基本都通过注解来实现权限隔离
        //※ 过滤器的匹配按照代码顺序,小范围的代码应该置于大范围的前面,否则会造成只匹配大范围
//        filterChainDefinitionMap.put("/user/update","perms[user:update]");
//        filterChainDefinitionMap.put("/user/update","roles[role:admin]");

        //设置authc过滤器,允许所有已登录的用户可以进行访问的页面
        filterChainDefinitionMap.put("/user/*","authc");

        //设置anon,指定不拦截的页面
        filterChainDefinitionMap.put("/user/forgetPassword","anon");
        filterChainDefinitionMap.put("/static/libs/jquery-3.4.1.js","anon");
        //--路径过滤设置完毕--

        //设置登陆页面,进入需认证的authc页面失败后会跳转至登陆页面
        shiroFilterFactoryBean.setLoginUrl("/login");

        //设置perms页面,授权perms失败后会跳转至此
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        //最后将设置的map集合set进filter,并返回bean
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    //1.2创建defaultWebSecurityManger
    @Bean
    public DefaultWebSecurityManager getSecurityManager(UserRealm userRealm) {
        //创建一个安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将realm设置进安全管理器
        securityManager.setRealm(userRealm);
//        securityManager.setRealms();  //如果有多个realm则通过集合的方式设置

        //进行密码加密.前端获取的密码会经过此处进行加密后,与数据库进行匹配
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5"); //设置加密类型
        credentialsMatcher.setHashIterations(1);        //设置加密次数
//        credentialsMatcher.setHashSalted(true);       //过期方法,shiro团队出于安全考虑取消了盐的设置

        //将上述规则设置进自定义的realm中
        userRealm.setCredentialsMatcher(credentialsMatcher);

        //返回安全管理器
        return securityManager;
    }

    //1.3,创建realm,并为容器提供相应getRealm的方法
    @Bean
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

    //   ===================================================================================================================

    /**
     * shiro使用注解进行认证/拦截的方式,这一段全部复制即可
     */

    //Shiro生命周期处理器
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
    //需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    //开启shiro aop注解支持.因为使用代理方式,所以需要在配置文件中开启代码支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
                = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}