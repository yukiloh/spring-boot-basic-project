package com.example.redis;

import com.example.redis.model.UserWithLombok;
import lombok.Cleanup;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LombokTest {

    /**
     * 测试lombok.需要在maven中添加相关依赖.idea则需要额外的配置.个人不是很推荐使用
     */
    @Test
    public void testForLombok() throws FileNotFoundException {
        UserWithLombok user = new UserWithLombok(1);
        user.setUsername("aaa");
        System.out.println(user);

        //jdk1.7(可能)后针对实现了java.io.Closeable的类可以通过try来自动关闭
        //此时可以通过lombok的 @Cleanup 实现自动关闭功能
        @Cleanup InputStream inputStream = new FileInputStream("path");
    }

}
