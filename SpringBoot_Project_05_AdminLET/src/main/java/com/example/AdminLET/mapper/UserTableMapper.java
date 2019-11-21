package com.example.AdminLET.mapper;

import com.example.AdminLET.domain.UserTable;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.MyMapper;

@Repository
public interface UserTableMapper extends MyMapper<UserTable> {
}