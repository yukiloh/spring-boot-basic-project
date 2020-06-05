# 在springboot中使用mongodb的案例

使用spring提供的spring-data-mongodb来操作数据库,非常类似于spring-data-jpa
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

## mongodb的安装

###创建docker-mongodb

官方推荐通过docker-compose来创建,如果win10为企业版推荐使用此方法  

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

官方的docker镜像地址: https://hub.docker.com/_/mongo

### 在win10上创建

本人一般将配套开发环境配置在arm32的nas上(db,nginx之类,方便终端切换时不会丢失环境)  
由于官方在某个古老版本后停止了对arm32的支持,所以只能配置在win10上  

mongodb分为2个版本,社区版和企业版,大部分功能是一致的,需要了解详细可以参考: https://www.cnblogs.com/likehua/p/3796172.html  
官方地址: https://www.mongodb.com/try/download/community  

下载完成后运行msi,选择路径之类的不再赘述
**注意,mongo-compass是监控mongodb的配套工具,可以不进行安装,因为有其他的gui工具可以代替监控/查看数据**  

安装完成后在安装目录的bin文件夹下,运行cmd  
输入以下命令运行mongodb

```vb
# d:\... 路径需要自行修改,注意**必须**确保路径文件夹是存在的
mongod --dbpath d:\mongodb\data\db
```

需要关闭时可以ctrl + c

参考:  https://www.runoob.com/mongodb/mongodb-window-install.html
