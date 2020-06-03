# springboot的基础演示
关于springboot的基础演示  
~~因为是早期生成的,有许多不完善的地方~~已更新  
主要进行了以下几点:    
- `QuickstartController` springboot中最基础的演示,包含了@RestController的应用,以及get的传参

- `PageController` 演示了如何通过@Controller路由到页面

- `ValueController` 演示了如何通过@Value获取

- `@ConfigurationProperties`的用法,与@Value相似,可以注入到javabean中  
  演示案例在测试文件`ConfigurationPropertiesTestContext`中
  
- `MybatisController` 演示了如何mybatis与springboot如何结合,以及事务的开启


## 拓展补充:关于@InitBinder(项目中未使用)

一般用于input标签中(表单),使用ognl表达式向后端发送数据(user.name,user.id; addr.name,addr.id)  
遇到后缀相同的时候springMVC无法进行解析,因此需要使用@InitBinder来区分各自的后缀  

>参考:https://blog.csdn.net/qq_24505127/article/details/54236583
