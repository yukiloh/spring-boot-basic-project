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

### 创建docker-mongodb

官方推荐通过docker-compose来创建  
由于官方在某个古老版本后停止了对arm32的支持,本人表示很无奈...

```yaml
version: '3.1'

services:
  # MongoDB 数据库
  mongo:
    image: mongo
    restart: always
    ports:
      - 27017:27017
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: example
  
  # 图形化的 MongoDB web 客户端管理工具,可以选择不开启
  mongo-express:
    image: mongo-express
    restart: always
    ports:
      - 8081:8081
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: root
      ME_CONFIG_MONGODB_ADMINPASSWORD: example

```

官方地址: https://hub.docker.com/_/mongo