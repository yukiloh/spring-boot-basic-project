package com.example.jwtsecuritydemo.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 通过filter来拦截登陆行为(指访问/login)
 * spring提供了默认的登陆方式(loginForm)
 * 如果需要自定义登陆方式(如这里使用json信息),需要继承 AbstractAuthenticationProcessingFilter
 * 然后重写attemptAuthentication
 */
public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 通过构造函数,指定拦截request的路径和访问方式
     */
	public MyUsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login", "POST"));
	}

    /**
     * spring在bean初始化时进行设置,这里检查一些配置是否为空
     * 详细参考: https://blog.csdn.net/u013013553/article/details/79038702
     */
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(getAuthenticationManager(), "AuthenticationManager must be specified");
		Assert.notNull(getSuccessHandler(), "AuthenticationSuccessHandler must be specified");
		Assert.notNull(getFailureHandler(), "AuthenticationFailureHandler must be specified");
	}

    /**
     * 从json中获取 username 和 password
     */
	@Override
	public Authentication attemptAuthentication(
	        HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException, IOException, ServletException {
	    //从请求中获取json信息
		String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
		String username = null, password = null;

		//从json信息中获取username和password
		if(StringUtils.hasText(body)) {
		    JSONObject jsonObj = JSON.parseObject(body);
		    username = jsonObj.getString("username");
            password = jsonObj.getString("password");
        }

		username = username == null ? "":username.trim();
		password = password == null ? "":password.trim();

        //所有的请求经过filter后都会封装为(Authentication)token,再交给AuthenticationManager
		UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);
		return this.getAuthenticationManager().authenticate(token);

		//之后AuthenticationManager会把token交给AuthenticationProvider进行验证
	}

}
