# 本项目主要演示了springboot与thymeleaf的使用

主要内容在`/resources/templates/index.html`中,建议在浏览器中查看  
一些thymeleaf的配置在yml中,都有注解

## thymeleaf的依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

## todo

修改默认错误页面(404,500)  
因为大部分时候使用thymeleaf或者其他模板引擎,不是很重要暂时搁置