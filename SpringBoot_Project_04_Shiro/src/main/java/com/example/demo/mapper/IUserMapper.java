package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IUserMapper {

    /*使用了when...then...语句*/
    @Select("SELECT userName,password,perm,(case user_table.role when 1 then 'role:user' when 2 then 'role:manger' when 3 then 'role:admin' end) as role FROM user_table   where userName = #{username};")
    User findUserByUsername(String username);
}
