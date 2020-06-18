package com.example.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;

//@SpringBootTest
class SpringSecurityApplicationTests {

    @Test
    void contextLoads() {
        String admin = BCrypt.hashpw("admin", BCrypt.gensalt());
        System.out.println(admin);


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        arrayList.add("d");

        for (String s : arrayList) {
            if (s.equals("b")) {
                System.out.println(s);
                return;
            }
        }
        System.out.println("fin");
    }

}
