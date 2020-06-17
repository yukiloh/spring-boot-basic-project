package com.example.thymeleaf;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ThymeleafApplicationTests {


    @Test
    public void contextLoads () {
        int end = Integer.MAX_VALUE;
        int start = end-2;
        int count = 0;

        System.out.println(end);
        System.out.println(start);

        start++;
        System.out.println(start);
        start++;
        System.out.println(start);
        System.out.println(start==end);
//
//        for (int i = start; i <= end; i++) {
//            count++;
//            System.out.println(count);
//        }
//        System.out.println(count);
    }

}
