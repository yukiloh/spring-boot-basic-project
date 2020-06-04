# 在springboot中使用mongodb的案例

参考: https://www.jianshu.com/p/0d49aa4ffc75
原文非常完善,在此基础上补充一些建表时的注解以及其他查询方式

## 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

## 配置步骤

1. 配置类中配置mongo的地址(或用户名密码)

2. 创建一个实体类,并基于这个实体类再去创建一个repository,需要继承spring提供的MongoRepository  
   类似于jpa的repository,会提供大量操作数据库的方法
   
3. 添加crud语句(本案例在service中),然后在controller中发起请求执行数据库操作

如果需要添加一个用户可以使用以下json

```json
{
    "studentId": "201311611405",
    "age":24,
    "gender":"男",
    "name":"一个优秀的废人"
}
```