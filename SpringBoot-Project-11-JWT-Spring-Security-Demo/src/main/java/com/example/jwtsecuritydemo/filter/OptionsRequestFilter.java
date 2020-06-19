package com.example.jwtsecuritydemo.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ajax在发送真实请求之前,会向服务端发送OPTIONS请求
 * 检查服务端是否支持
 * 而服务器端只需要通过过滤器来返回response即可
 */
public class OptionsRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getMethod().equals("OPTIONS")) {
			response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
			response.setHeader("Access-Control-Allow-Headers", response.getHeader("Access-Control-Request-Headers"));
			return;
		}
		filterChain.doFilter(request, response);
	}

}
