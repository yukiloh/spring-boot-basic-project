# spring-security 结合 jwt 的登陆案例

参考地址: https://www.jianshu.com/p/d5ce890c67f7

### 各种filter的用途解释

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



