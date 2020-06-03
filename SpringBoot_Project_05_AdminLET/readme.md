# 本项目用于展示基于adminLET模板而生成的页面
期间会使用到MBG,TKMybatis,adminLET模板，iframe标签，vue等工具整合的使用  
该项目是我在学习js(系统的),vue(完整的),jpa之前所做的项目,现在看起来非常简陋  
todo 需要整理的东西非常多...

## 关于mybatis-generator(MBG)的使用

可以根据model自动生成表格,可以和tk.mybatis合用来实现jpa的效果  
与jpa不同的是,mbg根据数据库表来生成实体类,jpa则是根据实体类生成数据库表        
虽然在学习了jpa后我觉得这有点脱裤子放屁.而且分页还需要依赖GitHubPage这样的插件,jps是可以自动生成分页信息的  

### 使用步骤

1. 添加依赖依赖:(注意修改generatorConfig.xml的位置和依赖的sql依赖,此处是maria)
```xml
<build>
    <plugins>
        <!--mybatis代码生成-->
        <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.5</version>
            <configuration>
                <configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
                <overwrite>true</overwrite>
                <verbose>true</verbose>
            </configuration>
            <dependencies>
                <!--数据库为maria-->
                <dependency>
                    <groupId>org.mariadb.jdbc</groupId>
                    <artifactId>mariadb-java-client</artifactId>
                    <version>${mariadb.version}</version>
                </dependency>
                <!--引入mybatis的(tk)依赖-->
                <dependency>
                    <groupId>tk.mybatis</groupId>
                    <artifactId>mapper</artifactId>
                    <version>3.4.4</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>

```

2. 添加generatorConfig.xml,配置其中的自定义属性(在xml中查看详细)

3. 通过maven的plugins生成相关数据


## 关于tk.mybatis

国人制作的一款,提供通用单表增删改查的工具(不支持通用多表联合查询,我提了issue了)

1. 创建自定义Mapper类(需要指定泛型),选择需要继承Mapper(来实现不同的sql语句,类似于jps的repository)  
例如:
```java
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
//...
}
```
详细接口文档:https://mapperhelper.github.io/all/

2. 在入口类添加@MapperScan,并指定mapper的路径(可以添加多个)
例如:`@MapperScan("com.example.adminLET.mapper")`

