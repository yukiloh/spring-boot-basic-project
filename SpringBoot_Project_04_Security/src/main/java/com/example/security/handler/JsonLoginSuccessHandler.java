package com.example.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/17 20:47
 * 登陆成功后,向客户端发送json token
 */
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , Authentication authentication
    ) throws IOException, ServletException {
        //todo 生成token,并把token加密信息缓存
        String token = "token";
        httpServletResponse.setHeader("token",token);
    }
}
