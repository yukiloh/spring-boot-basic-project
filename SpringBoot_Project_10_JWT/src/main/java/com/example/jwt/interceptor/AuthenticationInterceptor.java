package com.example.jwt.interceptor;

import com.example.jwt.annotation.PassToken;
import com.example.jwt.annotation.UserLoginToken;
import com.example.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * @author jinbin
 * @date 2018-07-08 20:41
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object object
    ) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token

        // 如果不是映射到方法直接通过
        // 关于第三个参数Object以及HandlerMethod可以参阅官方文档
        // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/method/HandlerMethod.html
        if(!(object instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();

        //检查controller是否包含@PassToken,有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        //检查有controller是否包含@UserLoginToken,有则验证jwt
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);

            // 执行认证
            if (userLoginToken.required()) {

                //没有token,直接抛出RE异常
                if (token == null) throw new RuntimeException("无token，请重新登录");

                //进行检查token(checkToken中只会返回true,或者抛出异常)
                return jwtService.checkToken(token);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
