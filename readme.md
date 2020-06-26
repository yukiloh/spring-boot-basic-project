# 个人的spring boot相关学习笔记

每一个模块分别包含相应内容  
clone后通过maven导入相应模块即可
翻新后基本每个模块都添加了readme,方便查阅学习

- quickstart  
  包含一些springboot基础用法  

- redis  
  springboot中redis的使用(spring-boot-starter-data-redis)  
  ~~因为历史原因,其中也有lombok的一些用法展示~~已移动至utils项目中

- thymeleaf  
  一些基础的`thymeleaf`语法,都写在index.html中

- shiro  
  通过`shiro`实现认证和授权

- security  
  演示了如何使用`spring-security`,分4种方式,循序渐进的介绍使用以及配置方法

- easy mybatis  
  springboot中使用`mybatis-generator`和`tk.mybatis(通用mapper)`  
  以此来简化mybatis的配置  

- restControllerAdvice  
  springboot中异常捕获的方式,主要演示了全局异常的捕获

- ~~jackson~~  
  已被移至utils项目中

- jwt  
  通过java-jwt来使用jwt的案例  

- rabbitmq  
  只演示了基础用法  
  todo:进行进阶用法的补充  

- mongodb  
  通过一个crud的案例来介绍`spring-data-mongodb`的用法  
  语法与jpa极其类似
