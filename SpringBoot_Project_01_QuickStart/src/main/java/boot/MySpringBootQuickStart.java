package boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/*@SpringBootApplication:springboot的引导类*/ /*此注解意味该类为引导类*/ /*必须置于任意包下  */
/*其实继承的还是@Configuration(spring的配置类),所以此类下方是可以写@Bean来为sping提供数据源*/




@EnableTransactionManagement
@SpringBootApplication
public class MySpringBootQuickStart {

    /*springboot快速部署4步：
     * 1.创建maven工程
     * 2.添加springboot起步依赖
     * 3.编写引导类
     * 4.编写controller   */

    public static void main(String[] args) {
        /*进行引导springboot*/  /*参数为引导类的字节码，即@SpringBootApplication所标记的类；通常情况引导方法在引导类中（也可以分开）*/
        SpringApplication.run(MySpringBootQuickStart.class);
    }


}
