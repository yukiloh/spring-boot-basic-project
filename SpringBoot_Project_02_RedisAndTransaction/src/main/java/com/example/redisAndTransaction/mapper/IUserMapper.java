package com.example.redisAndTransaction.mapper;


import com.example.redisAndTransaction.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IUserMapper {

    @Select("SELECT * FROM user_table")
    List<User> findAll();

    @Select("SELECT money FROM user_table where id = #{id}")
    double findMoneyById(Integer id);

    @Update("UPDATE user_table SET money = #{money} WHERE id = #{id}")
    void updateMoneyByUserId(Double money,Integer id);

}
