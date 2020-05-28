package com.example.demo;

import com.example.demo.mapper.IUserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserWithLombok;
import com.example.demo.service.impl.TransactImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTests {


    @Autowired
    TransactImpl transact;

    @Test
    public void testForTransaction(){

        /*1.在入口Application中开启@EnableTransactionManagement
        * 2.在@Service下的方法上添加@Transactional
        * ※注意：
        * @Transactional只能注解至public上
        * 关于回滚失败：对于空指针、byZero之类的checked异常可以被回滚
        * 网络失败、文件读写失败等是无法进行回滚的
        * 事务会增加线程开销*/
        transact.transact();
        int a = 10/0;       /*主动添加异常*/
        /*如果byZero写在transact内，即使没有@Transactional也会进行异常回滚，写在外面则不会*/

    }
}
