package com.example.redisAndTransaction;

import com.example.redisAndTransaction.mapper.IUserMapper;
import com.example.redisAndTransaction.model.User;
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
import java.util.concurrent.TimeUnit;

/**
 * 通过springboot提供的RedisTemplate来获取redis中的值
 */

@RunWith(SpringRunner.class)    //junit的注解,用于设置测试环境.如果是springboot2.2后的已经不需要@Runwith了
                                //参见: https://blog.oio.de/2019/12/13/what-is-new-in-spring-boot-2-2/
@SpringBootTest
public class RedisTests {

    /**
     * 引入userMapper
     */
    @Autowired
    private IUserMapper userMapper;

    /**
     * 注入springboot自动配置的template，其泛型为key和value,2者只能为Object或String
     */
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

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
                    redisTemplate.opsForValue().set("userList", userList,300,TimeUnit.SECONDS);
                    System.out.println("--set the list into redis succeed!--");
                }
            }
        } else System.out.println("find in redisUtils!");

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
    @SuppressWarnings("unchecked")  //压制unchecked警告,或者可以使用 instanceof 来做类型检查
    public void contextLoads1() {

        //创建一条runnable,获取redis中的值
        Runnable runnable = () -> {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            List<User> userList = (List<User>) redisTemplate.opsForValue().get("userList");

            if (userList != null) {
                for (User user : userList) {
                    System.out.println(user);
                }
            }else System.out.println("list is null");
        };

        //设置线程池,执行多线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {
            executorService.submit(runnable);
        }

        // 延迟主线程,防止提前退出
        try {
            Thread.sleep(10000);
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

        // 随机获取一个key的value
        System.out.println(redisTemplate.randomKey());

        // 设置一个key的value，和存活时间
        redisTemplate.opsForValue().set(key, value,seconds, TimeUnit.SECONDS);

        // 取消key的过期时间（TTL = -1）
        redisTemplate.persist(key);

        // 获取key的过期时间
        Long expire = redisTemplate.getExpire(key);
        System.out.println(expire);

        // 设置key的过期时间
        redisTemplate.expire(key,seconds,TimeUnit.SECONDS);
    }


    /**
     * 补充1：一段演示redisTemplate的示例
     * 参考链接：https://www.jianshu.com/p/0fa4c100e9a9

    * 依赖：
    <!-- https://mvnrepository.com/artifact/org.springframework.data/spring-data-redis -->
     单体：
    <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-redis</artifactId>
    <version>2.2.2.RELEASE</version>
    </dependency>
     springboot：
     <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis -->
     <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-redis</artifactId>
     <version>2.2.1.RELEASE</version>
     </dependency>

     **/

    //补充2: redis也存在集群中的哨兵模式，只需要在配置文件中添加sentinel配置项即可（已写入yml中）
}
