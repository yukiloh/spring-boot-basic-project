package com.example.demo.service.impl;


import com.example.demo.mapper.IUserMapper;
import com.example.demo.service.ITransact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactImpl implements ITransact {


    @Autowired
    private IUserMapper userMapper;

    @Override
    @Transactional
    public void transact() {
        double exchange = 100;

        Integer userA = 1;
        double resultA = userMapper.findMoneyById(userA)-exchange;
        Integer userB = 2;
        double resultB = userMapper.findMoneyById(userB)+exchange;
        /*测试throw   */
        //        try {
//            if (resultA < 0) throw new RuntimeException("余额不足");
//        }catch (RuntimeException e){
//            e.printStackTrace();
//        }
        userMapper.updateMoneyByUserId(resultA,userA);
        userMapper.updateMoneyByUserId(resultB,userB);
    }
}
