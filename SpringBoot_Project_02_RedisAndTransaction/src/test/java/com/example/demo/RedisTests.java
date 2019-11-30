package com.example.demo;

import com.example.demo.mapper.IUserMapper;
import com.example.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {


    @Autowired
    private IUserMapper userMapper;

    /*注入springboot自动配置的template，其泛型只能为Object或String*/
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void contextLoads() {
        List<User> userList;

        /*序列化key，使得key不为乱码（value无所谓）*/
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        /*从redis中获取key=userList的值（通常返回的是object或string）*/
        List<User> userListByRedis = (List<User>) redisTemplate.opsForValue().get("userList");

        if (userListByRedis == null){
            synchronized (this){
                /*可能会出现object对象失效，所以再次获取对象；多线程中对象是不可靠的*/
                userListByRedis = (List<User>) redisTemplate.opsForValue().get("userList");
                if (userListByRedis == null){
                    System.out.println("checking in sql!");
                    userList = userMapper.findAll();
                    redisTemplate.opsForValue().set("userList",userList);        /*存储redis的key=userList*/
                }
            }
        }else System.out.println("find in redisUtils!");
        if (userListByRedis != null) {
            for (User u:userListByRedis) {
                System.out.println(u);
            }
        }


        /*多线程并发测试语句*/
        /*可以用下方法测试多线程并发，本案例因为未知原因无法测试*/
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 50; i++) {
//            executorService.submit(runnable);
//        }

        /*redis也存在集群中的哨兵模式，只需要在配置文件中添加sentinel配置项即可（yml）*/
    }




}
