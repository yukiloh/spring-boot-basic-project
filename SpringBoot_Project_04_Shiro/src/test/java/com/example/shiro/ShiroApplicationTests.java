package com.example.shiro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ShiroApplicationTests {


    /**
     * 获取md5,主要为了储存密码
     */
    @Test
    public void contextLoads() {
        String pw = "111111";
        String md5Str = DigestUtils.md5DigestAsHex(pw.getBytes());  //spring封装的md5加密方法,比较方便
        System.out.println(md5Str);
    }

    @Test
    public void contextLoads1() {
        String test = "啊,吧,c";
        int i = test.indexOf(",");
        boolean contains = test.contains(",");
        System.out.println(i);
        System.out.println(contains);
    }

}
