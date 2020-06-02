package com.example.restControllerAdvice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 进行全局异常捕捉处理
 */

//lombok的功能,可以替代
//private final Logger logger = LoggerFactory.getLogger(当前类名.class);
@Slf4j

//@RestControllerAdvice /*类似于controller和restController的区别*/
@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(RRException.class)
    public String apiExceptionHandler(RRException ex,Model model) {
        //log日志打印
        log.error("ApiException 异常抛出:", ex);

        /*向前端发送异常信息*/
        model.addAttribute("msg",ex);

        //路由到500页面
        return "/error/500";
    }


}
