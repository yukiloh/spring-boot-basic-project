package com.example.demo.service.impl;


import com.example.demo.constants.WebConstants;
import com.example.demo.domain.TbSysUser;
import com.example.demo.mapper.TbSysUserMapper;
import com.example.demo.service.LoginService;
import com.example.demo.service.RedisService;
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

    @Autowired
    private RedisService redisService;

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    /*注册*/
    @Override
    public void register(TbSysUser tbSysUser) {

    }

    @Override
    public Boolean AllowToRegistered(String loginCode) {

        return true;
    }


    /*登陆*/

    /**
     * 登录
     * 根据用户名从redis中查询,若存在
     * @param loginCode 登陆账号
     * @param plantPassword 明文密码
     * @return
     */
    @Override
    public String login(String loginCode, String plantPassword) {
        TbSysUser tbSysUser;
        String json = redisService.get(loginCode);

        /*若没有数据,则从数据库中查询，并存入redis*/
        if (json == null) {
            Example example = new Example(TbSysUser.class); /*tk提供的mybatis的查询工具*/
            example.createCriteria().andEqualTo("loginCode",loginCode);
            tbSysUser = tbSysUserMapper.selectOneByExample(example);
            String password = DigestUtils.md5DigestAsHex(plantPassword.getBytes()); /*明文加密，需要传入字节码*/
            if (tbSysUser != null &&tbSysUser.getPassword().equals(password)) { /*当user存在且密码正确时（遗漏判断user是否存在）*/
                /*找到tbSysUser，并存入redis,存活时间30min*/
                try {
                    String userJson = MapperUtils.obj2json(tbSysUser);
                    redisService.put(loginCode, userJson, WebConstants.QUATER_DAY);
                    return userJson;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;    /*如果密码不匹配返回null*/
        }
        /*若存在数据*/
        else return json;
    }
}
