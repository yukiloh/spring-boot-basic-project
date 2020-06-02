package com.example.quickstart.dao;

import com.example.quickstart.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IUserDao {

    @Select("select * from user_table")
    List<User> findAll();

}
