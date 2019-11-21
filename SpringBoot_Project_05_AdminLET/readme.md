- 使用iframe创建admin和post的页面，通过vue完成导航栏跳转页面
- 完成post页面







#### 关于mybatis-generator(MBG)的使用
1.添加依赖依赖:(注意修改generatorConfig.xml的位置和依赖的sql依赖,此处是maria)
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
                <!--此处不是mysql的依赖-->
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

2.添加generatorConfig.xml,配置其中的自定义属性
3.通过maven的plugins生成

#### 关于tk.mybatis
国人制作的一款,提供通用单表增删改查的工具(不支持通用多表联合查询!)

1.创建自定义Mapper类(需要指定泛型),可以继承Mapper等
例如:public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
详细接口文档:https://mapperhelper.github.io/all/

2.在入口类添加@MapperScan,并指定mapper的路径(可以添加多个)
例如:@MapperScan("com.example.AdminLET.mapper")











#### 备份数据

var vmMessages = new Vue({
        el: "#messages",
        data: {
            messages: [
                {
                    message: [
                        {username: "Tom"},
                        {message: "我下面有人"},
                        {time: "1小时以前"},
                        {avatar: "/static/img/user1-128x128.jpg"},

                    ]
                },
                {
                    message: [
                        {username: "Jerry"},
                        {message: "我上面有人"},
                        {time: "1小时以前"},
                        {avatar: "/static/img/user8-128x128.jpg"},
                    ]
                },
                {
                    message: [
                        {username: "Spark"},
                        {message: "你们俩等着"},
                        {time: "1小时以前"},
                        {avatar: "/static/img/user6-128x128.jpg"},
                    ]
                },
            ],
        }
    });
    
    
    
{[{"account": {"uuid": 114514,"account": "admin@admin.com","perm": "admin","info": "管理员账号","lastLoginGMT": "今日"}"account": {"uuid": 1919}]"account": "admin@admin.com","perm": "admin","info": "管理员账号","lastLoginGMT": "今日"}}]}












