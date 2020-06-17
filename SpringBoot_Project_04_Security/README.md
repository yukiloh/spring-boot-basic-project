# Spring Security 的使用

比shiro复杂,但完全不配置也可以直接使用  
security主要通过核心适配器`WebSecurityConfigurerAdapter`来实现认证/鉴权  
本案例有4个适配器adapter,通过循序渐进的方式来演示如何配置spring-security  
通过`@Configuration`来选择启用哪个adapter来实现(一次只能开启一个)  

## 依赖

主要有2个

```xml
<!-- spring security -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- 另外需要web的相关功能-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## 使用

spring-security的核心配置即`WebSecurityConfigurerAdapter`,通过继承并配置该类可以实现不同的权限认证效果  
因此本案例会通过从简到繁的配置adapter,循序渐进的来说明spring-security如何使用  

注意,这里有2个概念需要**事先理解**  
本案例中,所有登陆的操作一般称为**认证**(spring-security中多以Authentication前缀)  
是否有资格访问一个页面一般称为**权限鉴定/鉴权**(Authorization-授权,spring-security中多以Access前缀)

### 1. 最简配置 SimpleWebSecurityConfigAdapter

案例在`SimpleWebSecurityConfigAdapter`下的configure方法内  
开启表单登陆`formLogin()`
即可进行最简配置便可以开启spring-security

### 2. 进行简单的路径和角色权限配置 SimpleCustomizeWebSecurityConfigAdapter

案例在`SimpleCustomizeWebSecurityConfigAdapter`中  
通过spring提供的接口,来对路径进行简单的权限分配  
比如通过`antMatchers("/user").hasRole("USER")`来为user路径设置角色
关于匹配线管的参考: https://www.jianshu.com/p/e3a9a8c4876c

### 3. 实现自定义配置

案例在`CustomizeWebSecurityConfigurerAdapter`  
实现大部分的自定义配置,会重写认证,鉴权,异常处理等等内容  
这里比较复杂,建议先浏览一遍spring-security工作流程图(`resources`文件夹下)  
或者坚果云在线查看(需要登陆): https://www.jianguoyun.com/p/DQUE9x8QjqbFCBjXuqMD
参考: https://blog.csdn.net/zhaoxichen_10/article/details/88713799

### 4. 通过注解配置健全路径

案例在`AnnotationWebSecurityConfigAdapter`  
需要搭配`AnnotationController`中的接口  

spring-security默认关闭了注解  
需要在adapter类上手动启用`@EnableGlobalMethodSecurity`,并进行一些配置才可以使用注解进行权限拦截  
参考: https://www.jianshu.com/p/77b4835b6e8e

## 一些不重要的补充

### @EnableWebSecurity

组合注解,添加在`WebSecurityConfigurerAdapter`类上  
内部启用了`@Configuration`和`@EnableGlobalAuthentication`  
如果是非springboot项目需要启用这个注解  
但在springboot中,因为`SpringBootWebSecurityConfiguration`的原因  
只要引入spring-security就会自动开启,所以不是必须添加的  
参考: https://www.iteye.com/blog/fengyilin-2410779

