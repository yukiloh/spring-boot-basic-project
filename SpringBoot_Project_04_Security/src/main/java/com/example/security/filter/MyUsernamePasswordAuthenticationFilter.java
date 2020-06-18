package com.example.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/17 18:29
 */
@Component
public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public MyUsernamePasswordAuthenticationFilter() {
        //拦截url为 "/login" 的POST请求
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        //从json中获取username和password.request.getInputStream()会接收multipart/form-data类型的数据
        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));

        String username = null, password = null;
        if(StringUtils.hasText(body)) {
            Map<String, String> jsonObj =
                    new ObjectMapper().readValue(body, new TypeReference<Map<String, String>>() {});
            username = jsonObj.get("username");
            password = jsonObj.get("password");
        }
        System.out.println(username+password);

        if (username == null)
            username = "";
        if (password == null)
            password = "";
        username = username.trim();
        //封装到token中提交
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

}