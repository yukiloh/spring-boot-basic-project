package com.example.redis.mapper;


import com.example.redis.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IUserMapper {

    @Select("SELECT * FROM user_table")
    List<User> findAll();

}
