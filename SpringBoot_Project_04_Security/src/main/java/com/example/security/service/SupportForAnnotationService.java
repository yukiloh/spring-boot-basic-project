package com.example.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/15 15:32
 *
 */
@Service
public class SupportForAnnotationService {

    //权限拦截的注解也可以添加在service层
    @Secured("ROLE_ADMIN")
    public boolean deleteUser(Integer id) {
        System.out.println("删除了用户: "+id);
        return true;
    }

    //todo 实际开发中,可以通过getUser来判断用户,然后进行操作限制
    @PostAuthorize("returnObject %2 == 0 ")
    public Integer isEven(Integer number) {
        System.out.println("输入的数字是: "+number);
        return number;
    }
}
