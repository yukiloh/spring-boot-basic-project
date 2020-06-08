package com.example.quickstart;

import com.example.quickstart.domain.TestProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigurationPropertiesTestContext {

    /**
     * 启用@ConfigurationProperties的方法:
     * 1. 在配置文件中创建数据项目.例如:test.var1,test.var2
     * 2. 创建javabean,添加@ConfigurationProperties,并填写prefix.注意,属性名必须与配置文件中的一致!
     * 3. 在springboot的config类中启用@EnableConfigurationProperties({...}),value中填写@ConfigurationProperties所标记的类,可以是集合
     * 4. 在需要的地方@Autowired引入
     */

    @Autowired
    private TestProperties testProperties;

    @Test
    public void testContext() {
        System.out.println(testProperties.getVar1());
        System.out.println(testProperties.getVar2());
    }
}
