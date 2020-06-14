package com.example.security.handler;

import com.example.security.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/14 14:29
 * 全局异常捕获器(无法捕获security抛出的2种异常的...)
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
        logger.error("Exception 异常抛出:", ex);

        return ex.getMessage();
    }
}
