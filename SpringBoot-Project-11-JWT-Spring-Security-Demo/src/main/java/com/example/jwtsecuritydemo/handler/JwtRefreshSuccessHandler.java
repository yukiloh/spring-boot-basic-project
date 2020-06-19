package com.example.jwtsecuritydemo.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwtsecuritydemo.model.JwtAuthenticationToken;
import com.example.jwtsecuritydemo.service.JwtUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 负责刷新token,通过jwt认证成功后执行
 * 会判断签发时间和当前时间,是否超过了预设的间隔时间(tokenRefreshInterval),如果是则进行刷新
 * 之后再交给其他的filter
 */
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {
	
	private static final int tokenRefreshInterval = 300;  //刷新间隔5分钟

    private final JwtUserService jwtUserService;
	
	public JwtRefreshSuccessHandler(JwtUserService jwtUserService) {
		this.jwtUserService = jwtUserService;
    }

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
	    //获取解析后的jwt
		DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();

		//传入jwt签发,判断是否需要刷新token
		boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
		if(shouldRefresh) {
            String newToken = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
            response.setHeader("Authorization", newToken);
        }	
	}
	
	protected boolean shouldTokenRefresh(Date issueAt){
	    //LocalDateTime比Date类型可读性更强. ZoneId.systemDefault(): 获取当前时区
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());

        //签发时间+间隔是否大于当前时间,如果是则返回true
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }

}
