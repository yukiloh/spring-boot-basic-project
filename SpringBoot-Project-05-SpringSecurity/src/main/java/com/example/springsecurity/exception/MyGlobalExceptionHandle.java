package com.example.springsecurity.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 14:29
 * 全局异常捕获器
 */
@RestControllerAdvice
public class MyGlobalExceptionHandle {

    private Logger logger = LoggerFactory.getLogger(MyGlobalExceptionHandle.class);

    @ExceptionHandler(MyException.class)
    public String apiExceptionHandler(MyException ex) {
        logger.error("MyException 异常抛出:", ex);

        return ex.getMsg()+"   "+"code: "+ex.getCode();
    }

    @ExceptionHandler(Exception.class)
    public String allExceptionHandler(Exception ex) {
        //todo 这里粗略处理.实际业务中,需要对每个异常做精细化分(认证异常也丢给jvm就很奇怪了)
        logger.error("Exception 异常抛出:", ex);

        return ex.getMessage();
    }
}
