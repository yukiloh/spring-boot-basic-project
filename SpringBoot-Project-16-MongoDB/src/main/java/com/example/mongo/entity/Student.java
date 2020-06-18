package com.example.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/4 23:55
 */
@Document("Student")        //定义collection,如果不添加@Document则默认使用javabean的名称
public class Student {

    @Id                     //设置主键
    private String id;

    @Indexed                //标识该字段为索引,@CompoundIndex 则是复合索引
    private String studentId;
    private Integer age;

    @Field("name")          //设置字段别名
    private  String name;

    //@Transient            //排除字段
    private String gender;

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
