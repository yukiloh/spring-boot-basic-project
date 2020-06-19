# spring-security 结合 jwt 的登陆案例

本demo根据博文: https://www.jianshu.com/p/d5ce890c67f7
创作而成,对大部分语句/方法添加了自己的理解

## 运作流程

单纯文字比较难说请,可以查看/resources 下的工作流程图(部分不重要的类省略了)  
或者查看坚果云,需要注册  
https://www.jianguoyun.com/p/DThEqPsQjqbFCBj7laUD  

## 一些关键性的概念

- filter
  所有请求会先经过filter,如果需要认证则传入AuthenticationManager

- AuthenticationManager
  认证管理器,本身不进行认证,会把认证任务调度给不同的provider
 
- AuthenticationProvider
  进行认证工作.类似于shiro的realm,一个认证方式对应一个provider
  
- AuthenticationToken
  请求会先经过filter,然后封装为AuthenticationToken,再传入AuthenticationManager  
  本案例中,使用json登陆时使用的是UsernamePasswordAuthenticationToken  
  而进行jwt认证是使用的是自定义的JwtAuthenticationToken(需要继承AbstractAuthenticationToken)  
  
- SecurityContext(未使用)
  认证后会存入容器中,方便获取当前用户的信息.类似于shiro的SecurityUtils.getSubject()
  
## 各种filter的用途解释

spring-security中的filter非常多,通过继承可以实现不同的效果

- SecurityContextPersistenceFilter
  用于将SecurityContext放入Session的Filter
  
- UsernamePasswordAuthenticationFilter
  用于登录认证,会生成用于认证的toke,提交到AuthenticationManager  
  如果认证失败会直接抛出异常
  
- RememberMeAuthenticationFilter
  通过cookie来实现remember me功能的filter
  
- AnonymousAuthenticationFilter
  如果一个请求在到达这个filter之前,SecurityContext没有初始化  
  则这个filter会默认生成一个匿名SecurityContext,这在支持匿名用户的系统中非常有用
  
- ExceptionTranslationFilter
  捕获所有Spring Security抛出的异常,并决定处理方式
  
- FilterSecurityInterceptor
  权限校验的拦截器,访问的url权限不足时会抛出异常
  
所有Filter的顺序可以在`FilterComparator`中查看


## 其他补充
 
### 关于关闭session

原博文的关闭session方法在当前版本不起作用,应该这么写: 
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
}
```
参考: https://www.baeldung.com/spring-security-session



