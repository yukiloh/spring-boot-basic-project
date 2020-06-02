package boot.controller;

import boot.dao.IUserDao;
import boot.domain.User;
import boot.service.ITestService;
import boot.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/*控制器Controller*/   /*必须和boot在同一文件夹*/
@RestController     /*包含了@Controller*/
@ConfigurationProperties(prefix = "area")   /*用于匹配properties中的前缀*/
@PropertySource("classpath:application.yml")    //用于指定@Value所读取的配置文件,不设置则默认为application.properties/yml
public class    SpringBootQuickStartController {

    /*演示application.yml提供数据*/   /*优点:获取方便;缺点:获取多个属性时繁琐,必须每次都写@Value*/
    @Value("${name}")
    private String namee;

    /*演示获取数组*/
    @Value("${city[0]}")
    private String  city;
    /*二级资源*/
    @Value("${area.name}")
    private String  areaName;

    /*通过配置类的注解@ConfigurationProperties中匹配前缀prefix来获取area中的name和location,并且需要设置get&set方法*/
    private String name;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    @RequestMapping("/")
    @ResponseBody   /*结合返回类型String，直接在页面打印String值*/
    public String quickStart(){
        System.out.println("hello spring boot");

        return "hello springboot"+
                "<br>"+
                namee+
                "<br>"+
                city+
                "<br>"+
                areaName+
                "<br>"+
                name+location;
    }

    @Autowired
    private IUserDao userDao;

    @RequestMapping("/mybatis")
    @ResponseBody   /*结合返回类型String，直接在页面打印String值*/ /*其实返回的是json数据*/
    public List<User> quickQuery(){
        System.out.println("hello spring boot");
        return userDao.findAll();
    }

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String testService(String msg){
        return testService.TestDoSomething(msg);
    }
}