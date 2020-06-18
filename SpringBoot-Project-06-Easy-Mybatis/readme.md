# 本项目用于演示如何使用mybatis-generator和通用mapper

mybatis生成查询语句以及创建实体类都是重复性无意义的劳动,使用这两款插件可以提高工作效率  
二者的功能类似于jpa(注意,是2者结合后的功能),但利于mybatis手动挡+许多公司都使用,反而是jpa没有人气

- `mybatis-generator`: 快速生成实体类和mapper接口/xml文件

- `通用mapper`: tk.mybatis , 为mybatis提供大量增删改查方法  
  
## 关于mybatis-generator(MBG)的使用

可以根据model自动生成表格,可以和tk.mybatis合用来实现jpa的效果  
与jpa不同的是,mbg根据数据库表来生成实体类,jpa则是根据实体类生成数据库表  

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

2. 添加generatorConfig.xml,配置其中的自定义属性(详细配置写在xml中)

3. 通过maven的plugins生成相关实体类以及mapper


## 关于tk.mybatis

国人制作的一款,提供通用单表增删改查的工具(注意,不支持通用多表联合查询)  
详细: https://blog.csdn.net/isea533/article/details/83045335  

### 创建方法

1. 创建自定义Mapper类(需要指定泛型),选择需要继承Mapper(来实现不同的sql语句,类似于jps的repository)  
例如:  

```java
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
//...
}
```

使用参考:  https://mapperhelper.github.io/all/  

2. 在入口类添加@MapperScan,并指定mapper的路径(如需要多个可以添加数组)
例如:`@MapperScan("com.example.easymybatis.mapper")`

3. 使用相应mapper即可进行数据库操作.测试案例在`/test/java/EasyMybatisApplicationTests`中
