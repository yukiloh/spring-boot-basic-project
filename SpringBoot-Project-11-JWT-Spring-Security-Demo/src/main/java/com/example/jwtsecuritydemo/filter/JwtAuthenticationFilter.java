package com.example.jwtsecuritydemo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.jwtsecuritydemo.configuration.JwtAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截并检查请求中的jwt
 * 会检查header中的"Authorization"关键词
 * OncePerRequestFilter,顾名思义一次请求中保证只会执行一次拦截
 * 这里的filter只会对jwt进行解析,并验证用户.检查是否有访问权限等工作会交给别的filter
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private RequestMatcher requiresAuthenticationRequestMatcher;
	private List<RequestMatcher> permissiveRequestMatchers;
	private AuthenticationManager authenticationManager;
	

	private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
	private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	public JwtAuthenticationFilter() {
	    //如果header中带有Authorization则拦截
		this.requiresAuthenticationRequestMatcher =
                new RequestHeaderRequestMatcher("Authorization");
	}

	//和MyUsernamePasswordAuthenticationFilter中afterPropertiesSet的一样,预检查
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(authenticationManager, "authenticationManager must be specified");
		Assert.notNull(successHandler, "AuthenticationSuccessHandler must be specified");
		Assert.notNull(failureHandler, "AuthenticationFailureHandler must be specified");
	}

    //从header中获取jwt
	protected String getJwt(HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		//apacheCommon的工具类
		return StringUtils.removeStart(jwt, "Bearer");
	}

    /**
     * 因为OncePerRequestFilter保证只执行一次
     * 因此内部的doFilter()会先通过request.getAttribute()来判断当前filter是否已执行
     * 若未执行则调用doFilterInternal方法,再交给子类来实现
     * 参考: https://www.jianshu.com/p/3d9b4cfe1a62
     */
	@Override
	protected void doFilterInternal(
	        HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain
    )
			throws ServletException, IOException {
	    //如果没有拦截到(header中没有Authorization),则放行
        //因为后续访问需要鉴权的页面有别的过滤器,这里放行无大碍
		if (!requiresAuthentication(request, response)) {
			filterChain.doFilter(request, response);
			return;
		}

		Authentication authResult = null;
		AuthenticationException failed = null;      //todo 这里是null,需要在catch中自定义异常类型
		try {
		    //获取jwt
			String token = getJwt(request);
			if(StringUtils.isNotBlank(token)) {
			    //解析jwt
				JwtAuthenticationToken authToken = new JwtAuthenticationToken(JWT.decode(token));
				//将解析出来的token传入AuthenticationManager,并调用provider进行验证
			    authResult = getAuthenticationManager().authenticate(authToken);
			} else {
				failed = new InsufficientAuthenticationException("JWT is Empty");
			}
		} catch(JWTDecodeException e) {
			logger.error("JWT format error", e);
			failed = new InsufficientAuthenticationException("JWT format error", failed);
		}catch (InternalAuthenticationServiceException e) {
			logger.error(
					"An internal error occurred while trying to authenticate the user.",
					failed);
			failed = e;
		}catch (AuthenticationException e) {
			// Authentication failed			
			failed = e;
		}

		//认证成功
		if(authResult != null) {
		    //传入下一个filter
		    successfulAuthentication(request, response, filterChain, authResult);
		} else if(!permissiveRequest(request)){
		    //认证失败(且请求的地址不在ignore名单中)
			unsuccessfulAuthentication(request, response, failed);
			return;
		}

		filterChain.doFilter(request, response);
	}

	//认证失败时候的处理
	protected void unsuccessfulAuthentication(
	        HttpServletRequest request
            , HttpServletResponse response
            , AuthenticationException failed
    )
			throws IOException, ServletException {
        //清空context
		SecurityContextHolder.clearContext();

        //最终交给认证失败处理器
		failureHandler.onAuthenticationFailure(request, response, failed);
	}

	//认证成功的处理
	protected void successfulAuthentication(
	        HttpServletRequest request
            , HttpServletResponse response
            , FilterChain chain
            , Authentication authResult
    )
			throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(authResult);
		successHandler.onAuthenticationSuccess(request, response, authResult);
	}

    //放行一些不需要拦截的请求.比如允许用户匿名访问公开页面
    protected boolean permissiveRequest(HttpServletRequest request) {
        if(permissiveRequestMatchers == null)
            return false;
        for(RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
            if(permissiveMatcher.matches(request))
                return true;
        }
        return false;
    }

    //设置不需要拦截的路径
    public void setPermissiveUrl(String... urls) {
        if(permissiveRequestMatchers == null)
            permissiveRequestMatchers = new ArrayList<>();
        for(String url : urls)
            permissiveRequestMatchers .add(new AntPathRequestMatcher(url));
    }

    //==============================
    //后面都是一些初始化时设置的内容
	protected AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	protected boolean requiresAuthentication(
	        HttpServletRequest request
            , HttpServletResponse response
    ) {
        boolean matches = requiresAuthenticationRequestMatcher.matches(request);
        System.out.println(matches);
        return matches;
	}

	public void setAuthenticationSuccessHandler(
			AuthenticationSuccessHandler successHandler) {
		Assert.notNull(successHandler, "successHandler cannot be null");
		this.successHandler = successHandler;
	}

	public void setAuthenticationFailureHandler(
			AuthenticationFailureHandler failureHandler) {
		Assert.notNull(failureHandler, "failureHandler cannot be null");
		this.failureHandler = failureHandler;
	}

	protected AuthenticationSuccessHandler getSuccessHandler() {
		return successHandler;
	}

	protected AuthenticationFailureHandler getFailureHandler() {
		return failureHandler;
	}

}
