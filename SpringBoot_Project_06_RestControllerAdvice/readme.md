# 关于自定义全局异常

本项目会演示如何在springboot中添加一个全局异常捕获  

### 步骤
1. 定义一个异常(RRException),并设置其属性,例如msg和code等

2. 定义一个ExceptionHandler(GlobalExceptionHandle)，在类上添加注解@ControllerAdvice
  - 设置每个用于异常捕获的ExceptionHandler(apiExceptionHandler),并添加注解@ExceptionHandler(RRException.class),传入自定义异常的类文件
  
  - 类似于controller,可以传入RRException,Model等,完成向前端发送异常代码等任务
  
  - return可以返回一个ResponseBody(前端接受json文件)  
    或者返回String(进行html前缀匹配)  
    或者直接返回Object(Exception),此时会继续前往原controller指向的地址  
    ※如果filter抛出异常则不会进行捕获

## 关于exception中的serialVersionUID

主要用于版本控制,序列化和反序列化依赖于SUID，如果数值不固定会出现无法序/反序列化的情况

## 拓展补充关于@InitBinder(我也忘记这个干啥用的了,相关代码已经删除)

一般用于input标签中(表单),使用ognl表达式向后端发送数据(user.name,user.id; addr.name,addr.id)  
遇到后缀相同的时候springMVC无法进行解析,因此需要使用@InitBinder来区分各自的后缀  

>参考:https://blog.csdn.net/qq_24505127/article/details/54236583



