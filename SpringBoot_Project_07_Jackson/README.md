# Jackson的使用介绍

严格来说jackson和springboot没关系  
但放在java basic项目中感觉有点偏  
参考地址: https://www.cnblogs.com/guanbin-529/p/11488869.html  
yaml部分: https://www.baeldung.com/jackson-yaml

## Jackson的三个核心模块

- jackson-core
  核心模块,内部通过JsonGenerator和JsonParser来生成和解析json
  
- jackson-annotations(可选)
  注解包
  
- jackson-databind(可选)
  用于绑定对象.引入此模块会额外添加core和annotations模块
  因此开发时一般用此模块
  
## 依赖

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.11.0</version>
</dependency>

<!-- 启用jackson对yml格式的支持 -->
<dependency>
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-yaml</artifactId>
</dependency>

```

## 使用

具体案例都在测试类`JacksonTests`重,包含以下内容
- 基础用法,json和object互转,
- 注解的用法
- 各种读取,从文件读取/读取数组/读取为map类型
- 写入/读取yml文件(需要额外添加依赖)