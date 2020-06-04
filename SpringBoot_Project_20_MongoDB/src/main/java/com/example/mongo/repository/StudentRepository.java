package com.example.mongo.repository;

import com.example.mongo.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/5 0:08
 * 与jpa的repo类似,spring也为mongo提供大量方法供开发者使用
 */
@Repository
public interface StudentRepository extends MongoRepository<Student,String> {

    //也可以像jpa的命名查询那样自定义添加crud方法
    void deleteByName(String name);


    //补充: mongodb也可以使用源生查询(类似于jpa的动态查询Specification)
    //参考案例: https://segmentfault.com/a/1190000018058797
}
