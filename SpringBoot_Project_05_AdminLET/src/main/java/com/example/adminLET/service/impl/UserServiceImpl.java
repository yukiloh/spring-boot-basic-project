package com.example.adminLET.service.impl;

import com.example.adminLET.domain.UserTable;
import com.example.adminLET.mapper.UserTableMapper;
import com.example.adminLET.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserTableMapper userTableMapper;

    public UserTable selectOne(String uuid){
        UserTable userTable = new UserTable();
        userTable.setUuid(uuid);
        UserTable obj = userTableMapper.selectOne(userTable);

        return obj;
    }

    public List<UserTable> selectAll(){
        List<UserTable> obj = userTableMapper.selectAll();
        return obj;
    }


    /**
     *
     * @param pageNum   页数
     * @param pageSize  单页条数
     * @param userTable 实体类
     * @return
     */
    public PageInfo<UserTable> page(int pageNum, int pageSize,UserTable userTable) {
        PageHelper pageHelper = new PageHelper();
        PageMethod.startPage(pageNum,pageSize);
        PageInfo<UserTable> pageInfo = new PageInfo<>(userTableMapper.select(userTable));
        /*后续可以通过pageInfo获取getSize getTotal等*/
        return pageInfo;
    }

}
