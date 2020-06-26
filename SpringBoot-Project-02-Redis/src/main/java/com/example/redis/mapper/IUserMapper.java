package com.example.redis.mapper;


import com.example.redis.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * mybatis的mapper类
 * 当程序无法从redis中获取数据时,会从数据库中获取,并存入redis作为缓存
 */
@Mapper
@Component
public interface IUserMapper {

    @Select("SELECT * FROM user_table")
    List<User> findAll();

}
