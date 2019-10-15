package com.example.demo.shiro;

import com.example.demo.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /*关于bean的作用：使方法返回的对象交由spring管理（供其他方法使用）*/

    /*1.1创建shiroFilterFactoryBean*/
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        /*为bean工厂设置安全管理器*/
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        /*  shiro内置的过滤器,实现权限相关拦截：
            authc：所有已登陆用户可访问
            roles：有指定角色的用户可访问，通过[ ]指定具体角色，这里的角色名称与数据库中配置一致
            perms：有指定权限的用户可访问，通过[ ]指定具体权限，这里的权限名称与数据库中配置一致
            anon：所有用户可访问，通常作为指定页面的静态资源时使用       */

        /*创建shiro定义过滤器的map*/
        Map<String, String> filterChainDefinitionMap = new HashMap<>();

        /*perms过滤器*/    /*意为：访问/../..路径，需要perms[...]的权限*/
        filterChainDefinitionMap.put("/user/update","perms[user:update]");      /*过滤器的匹配按照代码顺序,小范围的代码应该置于大范围的前面,否则会造成只匹配大范围*/

        /*添加需要过滤器的 路径&过滤器类型(↓为authc过滤器)*/
        filterChainDefinitionMap.put("/user/*","authc");

        /*添加anon,指定不拦截的页面*/
        filterChainDefinitionMap.put("/user/forgetPassword","anon");
        filterChainDefinitionMap.put("/user/success","anon");

        /*设置登陆页面,进入authc页面失败后会跳转至登陆页面*/
        shiroFilterFactoryBean.setLoginUrl("/login");

        /*设置perms页面,perms失败后会跳转至此*/
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");


        /*最后将设置的map集合set进filter,并返回bean*/
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /*1.2创建defaultWebSecurityManger*/
    @Bean
    public DefaultWebSecurityManager getSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        /*关联realm*/
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    /*1.3创建realm*/
    @Bean
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

}