package com.example.demo.service.impl;


import com.example.demo.constants.WebConstants;
import com.example.demo.domain.TbSysUser;
import com.example.demo.mapper.TbSysUserMapper;
import com.example.demo.service.LoginService;
import com.example.demo.service.RedisService;
import com.example.demo.utils.MapperUtils;
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

    /**
     * 登录
     * 根据用户名从redis中查询,若存在
     * @param loginCode 登陆账号
     * @param plantPassword 明文密码
     * @return
     */
    @Override
    public TbSysUser login(String loginCode, String plantPassword) {
        TbSysUser tbSysUser;
        /*若有数据,从loginCode获取token，并刷新存活时间*/

        Example example = new Example(TbSysUser.class); /*tk提供的mybatis的查询工具*/
        example.createCriteria().andEqualTo("loginCode",loginCode);
        tbSysUser = tbSysUserMapper.selectOneByExample(example);
        String password = DigestUtils.md5DigestAsHex(plantPassword.getBytes()); /*明文加密，需要传入字节码*/
        if (tbSysUser != null && tbSysUser.getPassword().equals(password)) { /*当user存在且密码正确时（遗漏判断user是否存在）*/
            return tbSysUser;
        }else return null;    /*如果密码不匹配返回null*/
    }

    @Override
    public TbSysUser loginByToken(String token) {
        String user = redisService.get(token);
        if (user != null){
            redisService.refresh(token);
            try {
                return MapperUtils.json2pojo(user, TbSysUser.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
