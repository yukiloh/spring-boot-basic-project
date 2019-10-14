package boot.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootClassWithNoWebTest {


    /*演示使用springboot运行非web的方法（纯java程序）*/
    public static void main(String[] args) {
        /*为service实现类添加@Service*/

        /*创建spring容器*/
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootClassWithNoWebTest.class);
        /*创建名为noWebService的bean*/
        INoWebService noWebService = (INoWebService) context.getBean("noWebService");
        /*使用该bean调用方法*/
        System.out.println(noWebService.testService("hello world"));

        /*过程中不会使用任何web 容器（tomcat）*/



    }
}
