# 本项目用于演示shiro在springboot中的用法

## 项目的演示

shiro本身比较复杂,不是一篇md能说清楚的,这里只能略微指教一二  
本项目是借助thymeleaf来完成演示效果  
在配置完数据库后,可以进行登录账号  
不同的账号有不同的权限/角色,以此来实现shiro的权限管理

## shiro的核心运作方式

1. 通过shiro提供的subject来获取当前用户对象(获取用户输入的用户名密码等)  
2. 再交由安全管理器securityManger处理(转发,代理等等,开发者不会和他有接触)  
3. 再去关联realm实现权限认证
  
realm可以理解为进行验证的地方,**是shiro连接数据(数据库)的桥梁**  
如果有多套规则(比如支付宝登录,微信登陆...)则需要创建多个realm  
全局搜索`securityManager.setRealms`可以看到在那里如何设置多个realm

## 依赖和sql

```xml
<!-- shiro-spring -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>1.4.1</version>
</dependency>
```

sql语句在/resources/sql 中,另外需要自行配置yml中的数据库信息

## shiro的基础创建步骤

1. 首先,编写shiro的配置类ShiroConfig,分三步:

   1.1 创建shiroFilterFactoryBean
   `new ShiroFilterFactoryBean()`  
   通过设置shiroFilterFactoryBean完成shiroFilter的自定义  
     - 设置安全管理器setSecurityManager  
     - 设置过滤规则(map);shiro包含了四个内置的过滤器来实现权限相关拦截：  
       - authc：所有已登陆用户可访问  
       - roles：有指定角色的用户可访问，通过[ ]指定具体角色，这里的角色名称与数据库中配置一致  
       - perms：有指定权限的用户可访问，通过[ ]指定具体权限，这里的权限名称与数据库中配置一致  
       - anon：所有用户可访问，通常作为指定页面的静态资源时使用  

       例如,设置拦截,并在拦截后跳转至登陆页面setLoginUrl("/login");     

     - 最后将配置内容传入过滤器  
   `shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);`

    1.2 创建defaultWebSecurityManger，主要用于设置密码加密
    `new DefaultWebSecurityManager();`

    1.3 创建一个realm,并在config中创建一个getRealm的方法
    - 创建一个自定义的realm,需要继承`AuthorizingRealm`
    - 在config中创建`getUserRealm()`
    
2. 之后,创建一个自定义的realm，需要继承AuthorizingRealm，并重写认证和授权方法  

至此,shiro基础配置完成

---

## 关于认证和授权的

认证后会赋予用户user基础的访问权限,再进行授权赋予不同等级的权限

### 认证 Authentication 

在realm中通过doGetAuthenticationInfo进行认证,详细参见项目内

### 授权 Authorization

在realm中通过doGetAuthorizationInfo进行授权,详细参见项目内  

---  

## 关于shiro中注解的使用
### 一共5个重要注解
注解优先级:当一个类/方法/变量存在复数个下列注解时  
会按照下列注解的顺序依次进行验证(而非开发者进行注解的顺序),返回false则停止

- `@RequiresRoles`
当前Subject需拥有指定的角色,如无则方法不会执行并抛出UnauthorizedException异常

- `@RequiresPermissions`
当前Subject需拥有某些指定权限,如无则方法不会被执行并抛出UnauthorizedException异常

  1. ↑为资源权限控制的主要方案(主要注解)
  2. ↑注解的value可以为多个,可以通过logical=Logical.OR 或者AND(默认)的方式来使角色/权限为与/或的条件
  例如:```@RequiresPermissions(value={"perm:admin","perm:manger"},logical=Logical.OR)```    
  只要权限perm满足其中一个便可放行  
    
- `@RequiresAuthentication`  
验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时

- `@RequiresUser`
验证用户是否登录，此处有2种成功访问的方式:  
1. 成功登录（subject.isAuthenticated() 结果为true）  
2. 是被记忆的（subject.isRemembered()结果为true）  

- `@RequiresGuest` 
验证是否是一个guest的请求  
与@RequiresUser完全相反,RequiresUser  == !RequiresGuest    
如果@RequiresUser中的规则被允许,则这里会被拒绝  
此时subject.getPrincipal() 结果为null  

验证顺序(由上往下):
- RequiresRoles 
- RequiresPermissions 
- RequiresAuthentication 
- RequiresUser 
- RequiresGuest

### 开启shiro注解的步骤

1. ShiroConfig类中添加下列bean:  
 
```java
//Shiro生命周期处理器
@Bean(name = "lifecycleBeanPostProcessor")
public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
}
```
```java
//开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
//需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
@Bean
@DependsOn("lifecycleBeanPostProcessor")
public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    creator.setProxyTargetClass(true);
    return creator;
}
```

```java
//开启shiro aop注解支持.因为使用代理方式;所以需要开启代码支持;
@Bean
public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
            = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
}
```

2. 在controller的类/方法上添加相应注解即可使用

---

## 关于未授权的异常捕获

可以创建一个异常捕获器ExceptionHandleController来进行异常捕获  
出现授权错误时会跳转至相应页面  

```java
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

```