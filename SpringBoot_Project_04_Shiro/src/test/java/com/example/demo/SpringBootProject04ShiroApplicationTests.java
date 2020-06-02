package com.example.demo;

import com.example.shiro.mapper.IVerificationQuestionMapper;
import com.example.shiro.model.VerificationQuestion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootProject04ShiroApplicationTests {


    @Autowired
    private IVerificationQuestionMapper verificationQuestionMapper;

    @Test
    public void contextLoads() {
        Integer integer = verificationQuestionMapper.countVerificationQuestion();
        System.out.println(integer);
        int id = new Random().nextInt(integer + 1);
        VerificationQuestion question = verificationQuestionMapper.findVerificationQuestionById(id);
        System.out.println(question.getQuestion()+"    "+question.getAnswer());

    }

}
