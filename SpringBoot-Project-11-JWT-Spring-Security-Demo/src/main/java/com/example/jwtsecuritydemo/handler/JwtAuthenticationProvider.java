package com.example.jwtsecuritydemo.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwtsecuritydemo.model.JwtAuthenticationToken;
import com.example.jwtsecuritydemo.service.JwtUserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import java.util.Calendar;

/**
 * AuthenticationManager会调用本类,执行解析jwt的工作
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUserService userService;

    public JwtAuthenticationProvider(JwtUserService userService) {
        this.userService = userService;
    }

    /**
     * 进行jwt的解析,并调用service进行验证
     * 应该是JwtAuthenticationFilter中的这条语句 (使用全局搜索查找)
     * authResult = getAuthenticationManager().authenticate(authToken);
     * 执行了本方法
     */
    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //转换为自己定义的token,获取解析后的jwt
		DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();

		//检查过期
		if(jwt.getExpiresAt().before(Calendar.getInstance().getTime()))
			throw new NonceExpiredException("Token expires");

		//获取用户名(DecodedJWT中的方法)
		String username = jwt.getSubject();

		//调用service,传入username进行查询
		UserDetails user = userService.getUserLoginInfo(username);
		if(user == null || user.getPassword()==null)
			throw new NonceExpiredException("Token expires");
		String encryptSalt = user.getPassword();                    //将password作为salt

        //传入salt解析jwt
		try {
            Algorithm algorithm = Algorithm.HMAC256(encryptSalt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(username)
                    .build();
            verifier.verify(jwt.getToken());
        } catch (Exception e) {
            throw new BadCredentialsException("JWT token verify fail", e);
        }

		//认证成功后返回token,filter最后会将token存入SecurityContext
		JwtAuthenticationToken token = new JwtAuthenticationToken(user, jwt, user.getAuthorities());
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}

}
