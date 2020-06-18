package com.example.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 提供关于jwt相关的操作的方法(jwt验证的核心)
 * 详细使用可以参考java-jwt的github: https://github.com/auth0/java-jwt
 */
@Service("TokenService")
public class JwtService {

    // 过期时间,5分钟(可以改短来验证过期效果)
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
    // 设置密钥
    private static final String SECRET = "secret";

    @Autowired
    private UserService userService;

    /**
     * 将user封装为jwt
     */
    public String getToken(User user) {

        //设置过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        //将password作为token的密钥(临时的方案,不安全的做法)
        Algorithm sign = Algorithm.HMAC256(user.getPassword() + SECRET);

        //通过java-jwt来创建token
        String token = JWT.create()
                .withAudience(user.getId())                      // 将userId保存到Audience中
                .withIssuer("admin")                             // 设置签发者
                .withClaim("msg", "message")        // 自定义claim,这里也可以定义一些信息(键值对)
                .withSubject("hello world")                      // 这里也可以定义信息
                .withExpiresAt(date)                             // 设置过期时间
                .sign(sign);                                     // 设置签名

        return token;
    }

    /**
     * 检验jwt,不符合规格则抛出异常
     */
    public boolean checkToken(String token) {
        DecodedJWT jwt;

        try {
            //进行解码,获取userId
            String userId = JWT.decode(token).getAudience().get(0);        //从token中解析Audience,再从中获取userId

            //根据userId来查找user,不存在则抛出异常
            User user = userService.findUserById(userId);
            if (user == null) throw new RuntimeException("用户不存在,请重新登录");

            //校验token(password+secret),此处只进行算法Algorithm的校验,也可以添加claim和subject来多重校验
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword()+SECRET)).build();

            //进行验证,并获取jwt
            jwt = jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException("解析失败,请重新登陆");
        }

        //获取刚才存入jwt中的数据
        if (jwt != null) {
            //获取claim
            System.out.println("msg: "+jwt.getClaim("msg").asString());

            //获取subject
            System.out.println("subject: "+jwt.getSubject());

            //查看过期时间
            System.out.println("expires at: "+jwt.getExpiresAt());
        }
        return true;
    }
}
