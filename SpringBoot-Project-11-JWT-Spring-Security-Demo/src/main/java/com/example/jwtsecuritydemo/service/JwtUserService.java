package com.example.jwtsecuritydemo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

/**
 * 从数据库获取用户信息,提供给provider进行验证
 * 虽然尾缀是service,但更像是dao
 */
public class JwtUserService implements UserDetailsService {

    /**
     * 通过属性创建一个encoder
     * spring默认使用 BCryptPasswordEncoder (PasswordEncoder的实现类)
     */
	private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * 获取用户登陆信息
     */
	public UserDetails getUserLoginInfo(String username) {
	    //
        UserDetails user = loadUserByUsername(username);

        //todo 正式开发时应该从缓存或数据库中获取salt,例如:
        //String salt = redisTemplate.opsForValue().get("token:"+username);
		String salt = "123456ef";

    	//将salt放到password字段返回
    	return User.builder().username(user.getUsername()).password(salt).authorities(user.getAuthorities()).build();
	}

    /**
     * 设置用户登陆信息,返回jwt
     */
	public String saveUserLoginInfo(UserDetails user) {
	    //todo 正式开发时可以调用该方法实时生成加密的salt,例如:
        //BCrypt.gensalt();
		String salt = "123456ef";
		//然后将salt保存到数据库或缓存,例如:
        //redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);

		Algorithm algorithm = Algorithm.HMAC256(salt);
		Date expired = new Date(System.currentTimeMillis()+3600*1000);     //设置1小时后过期

        return JWT.create()
        		.withSubject(user.getUsername())
                .withExpiresAt(expired)
                .withIssuedAt(new Date())
                .sign(algorithm);
	}

	//todo 获取用户信息.正式开发应该从数据库中获取
	@Override

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))       //
                .roles("USER")
                .build();
	}

    /**
     * todo 删除用户登陆信息
     */
	public void deleteUserLoginInfo(String username) {
	}
}
