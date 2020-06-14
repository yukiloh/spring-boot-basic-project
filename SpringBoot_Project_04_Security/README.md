# SpringBoot Security 的使用

比shiro复杂,但完全不配置也可以直接使用  
security主要通过`WebSecurityConfigurerAdapter`来实现认证  
本案例有2个adapter(后期可能会增加)  
通过开关`@Configuration`来实现不同的认证效果

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

通过切换不同的`WebSecurityConfigurerAdapter`可以实现不同的权限认证效果  
因此采用循序渐进的方式来说明spring-security到底怎么用

### 1. 最简配置 SimpleWebSecurityConfigAdapter

在`SimpleWebSecurityConfigAdapter`下的configure方法内  
进行最简配置便可以开启spring-security

### 2. 进行简单的路径和角色权限配置 SimpleCustomizeWebSecurityConfigAdapter

案例在`SimpleCustomizeWebSecurityConfigAdapter`中  
对路径进行了简单的权限分配  
比如通过`antMatchers("/user").hasRole("USER")`来为user路径设置角色
                   

### 3. 实现自定义配置

案例在`CustomizeWebSecurityConfigurerAdapter`  
实现大部分的自定义配置  
这里比较复杂,建议查看`resources`文件夹下的流程图


## todo 注解的用法


