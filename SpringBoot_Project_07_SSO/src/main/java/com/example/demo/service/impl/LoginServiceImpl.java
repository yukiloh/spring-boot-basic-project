package com.example.demo.service.impl;


import com.example.demo.domain.TbSysUser;
import com.example.demo.mapper.TbSysUserMapper;
import com.example.demo.service.LoginService;
import com.example.demo.service.redisUtils.RedisService;
import com.example.demo.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

@Transactional(readOnly = true) /*声明式事务*/
@Service
public class LoginServiceImpl implements LoginService {
    /*使用log4j记录日志*/
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    /*注册*/
    @Override
    public void register(TbSysUser tbSysUser) {

    }

    /*登陆*/
    @Override
    public TbSysUser login(String loginCode, String plantPassword) {
        TbSysUser tbSysUser = null;

        String json = redisService.get(loginCode);

        /*没有数据,则从数据库中查询，并存入redis*/
        if (json == null) {
            Example example = new Example(TbSysUser.class); /*tk提供的mybatis的查询工具*/
            example.createCriteria().andEqualTo("loginCode",loginCode);
            tbSysUser = tbSysUserMapper.selectOneByExample(example);
            String password = DigestUtils.md5DigestAsHex(plantPassword.getBytes()); /*明文加密，需要传入字节码*/
            if (tbSysUser != null &&tbSysUser.getPassword().equals(password)) { /*当user存在且密码正确时（遗漏判断user是否存在）*/
                /*找到tbSysUser，并存入redis,存活时间30min*/
                try {
                    redisService.put(loginCode, MapperUtils.obj2json(tbSysUser),30 * 60);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return tbSysUser;
            }
            return null;    /*如果密码不匹配返回null*/
        }
        /*存在数据*/
        else {
            try {
                tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
            } catch (Exception e) {
                /*使用log4j记录日志，用于记录熔断*/
                logger.warn("触发熔断：{}",e.getMessage());
            }
        }
    return tbSysUser;
    }
}
