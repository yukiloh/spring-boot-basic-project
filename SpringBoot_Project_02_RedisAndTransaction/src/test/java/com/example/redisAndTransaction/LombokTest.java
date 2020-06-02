package com.example.redisAndTransaction;

import com.example.redisAndTransaction.model.UserWithLombok;
import org.junit.Test;

public class LombokTest {

    /**
     * 测试lombok.需要在maven中添加相关依赖.idea则需要额外的配置.个人不是很推荐使用
     */
    @Test
    public void testForLombok(){
        UserWithLombok user = new UserWithLombok();
        user.setUsername("aaa");
        System.out.println(user.getUsername());
    }

}
