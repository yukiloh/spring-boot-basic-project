# 用于演示redis的使用

以及lombok的一些用法

## 依赖

一般使用springboot的redis时直接去用starter,里面包含了spring-data-redis和redis线程池lettuce(可以通过依赖得以确认)

```xml
<!-- 单体-->
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-redis</artifactId>
    <version>2.2.2.RELEASE</version>
</dependency>
    
<!-- springboot starter-->
 <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>2.2.1.RELEASE</version>
</dependency>

<!--lombok-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>

```

## 使用

案例运行在/test/下
- `RedisTests` springboot中redis相关的使用(基于spring-boot-starter-data-redis依赖)
- `LombokTest` Lombok的使用展示(实体类在/model/UserWithLombok.java中)


