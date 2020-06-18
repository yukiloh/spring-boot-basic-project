package com.example.redis;

import com.example.redis.mapper.IUserMapper;
import com.example.redis.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 通过springboot提供的RedisTemplate来获取redis中的值
 */

@RunWith(SpringRunner.class)    //junit的注解,用于设置测试环境.如果是springboot2.2后的已经不需要@Runwith了
                                //参见: https://blog.oio.de/2019/12/13/what-is-new-in-spring-boot-2-2/
@SpringBootTest
public class RedisTests {

    /**
     * 注入userMapper
     */
    @Autowired
    private IUserMapper userMapper;

    /**
     * 注入springboot自动配置的template
     * 本案例主要通过spring提供的redisTemplate来进行存值/取值的操作
     * 注意,其泛型为key和value,2者只能为Object或String
     */
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;


    /**
     * 一个简单案例,尝试读取userList
     * 如果redis中有,则直接获取
     * 如果没有,则从数据库中获取,然后存入redis
     */

    @Test
    @SuppressWarnings("unchecked")  //压制unchecked警告,或者可以使用 instanceof 来做类型检查
    public void contextLoads() {
        List<User> userList;

        //序列化key，使key不为乱码（value无所谓）
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //从redis中获取key=userList的值（通常返回的是object或string）
        List<User> userListByRedis = (List<User>) redisTemplate.opsForValue().get("userList");

        //双锁检查保证单例,因为多线程中不能保证redis一定可以获取
        if (userListByRedis == null) {

            //锁对象本身,传入this
            synchronized (this) {
                //可能会出现object对象失效，所以再次获取对象；多线程中对象是不可靠的
                userListByRedis = (List<User>) redisTemplate.opsForValue().get("userList");

                //如果redis中未发现list,则从数据库中获取list,并设置到redis中
                if (userListByRedis == null) {
                    System.out.println("--checking in sql!--");
                    userList = userMapper.findAll();

                    //存入redis中,并设定过期时间300s
                    redisTemplate.opsForValue().set("userList", userList,3000,TimeUnit.SECONDS);
                    System.out.println("--set the list into redis succeed!--");

                    //查看结果
                    for (User user : userList) {
                        System.out.println(user);
                    }
                }
            }
        } else System.out.println("--find in redisUtils!--");

        //如果list不为空,则代表从redis中查找到目标,打印显示
        if (userListByRedis != null) {
            for (User u : userListByRedis) {
                System.out.println(u);
            }
        }
    }

    /**
     * 演示测试多线程并发获取redis中的数据
     */
    @Test
    @SuppressWarnings("unchecked")
    public void contextLoads1() {

        //创建一条runnable,获取redis中的值
        Runnable runnable = () -> {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            List<User> userList = (List<User>) redisTemplate.opsForValue().get("userList");

            if (userList != null) {
                for (User user : userList) {
                    System.out.println(user);
                }
            }else System.out.println("--list is null in redis--");
        };

        //设置线程池,执行多线程
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 50; i++) {
            executorService.submit(runnable);
        }

        // 延迟主线程,防止提前退出
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 其他一些redis的常用api
     */
    @Test
    public void contextLoads2() {
        // 初始化值
        String key = "key";
        String value = "value";
        long seconds = 1000;

        // 设置一个key的value，和存活时间
        redisTemplate.opsForValue().set(key, value,seconds, TimeUnit.SECONDS);

        // 取消key的过期时间（TTL = -1）
        redisTemplate.persist(key);

        // 获取key的过期时间
        Long expire = redisTemplate.getExpire(key);
        System.out.println(expire);

        // 设置key的过期时间
        redisTemplate.expire(key,seconds,TimeUnit.SECONDS);

        // 删除key
        redisTemplate.delete(key);

        // 随机获取一个key的value
        System.out.println(redisTemplate.randomKey());
    }

    /**
     * boundValueOps,与opsForValue类似,但前者会获取一个绑定了key的对象(BoundValueOperations)
     * 之后可以通过该对象重复对所绑定的key进行操作,无需多次绑定
     * 下为简单的取值操作
     */

    @Test
    @SuppressWarnings("unchecked")
    public void contextLoads3() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //获取boundValueOps对象
        BoundValueOperations<Object, Object> userList = redisTemplate.boundValueOps("userList");

        //获取所存的值
        List<User> list = (List<User>) userList.get();
        if (list!=null) {
            list.forEach(user -> System.out.println(user));
        }else System.out.println("list is null");
    }

    //其他使用：https://www.jianshu.com/p/0fa4c100e9a9

    //补充: redis也存在集群中的哨兵模式，只需要在配置文件中添加sentinel配置项即可（已写入yml中）

    //关于redis的事务: https://www.runoob.com/redis/redis-transactions.html
    //https://www.cnblogs.com/DeepInThought/p/10720132.html

}
