package com.example.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

/**
 * @SpringBootApplication springboot的引导类,必须放在包路径下的根目录
 * @EnableTransactionManagement 开启springboot中的事务管理功能.在service层或其方法上添加@Transactional即可开启事务
 */

@EnableTransactionManagement
@SpringBootApplication
public class MySpringBootQuickStart {

    /*springboot快速部署4步：
     * 1.创建maven工程
     * 2.添加springboot起步依赖
     * 3.编写引导类
     * 4.编写controller   */

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootQuickStart.class);            // 启动springboot.详细可以看源码
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));     // 设置时区
                                                                        // 某些utc时区的服务器中
                                                                        // 为了与数据库同步时区需要再次设定jvm的时区
    }
}


/**
 * 演示使用springboot运行非web的方法（纯java程序）
 */

//@SpringBootApplication
//public class MySpringBootQuickStart {
//
//    //注入service
//    @Autowired
//    private NoWebService noWebService;
//
//    public static void main(String[] args) {
//
//        /*创建spring容器*/
//        ConfigurableApplicationContext context = SpringApplication.run(MySpringBootQuickStart.class);
//        /*创建名为noWebService的bean*/
//        INoWebService noWebService = (INoWebService) context.getBean("noWebService");
//        /*使用该bean调用方法*/
//        System.out.println(noWebService.testService("hello world"));
//
//        /*过程中不会使用任何web 容器（tomcat）*/
//    }
//}
