package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Slf4j
/*对于不想每次都写
 *private final Logger logger = LoggerFactory.getLogger(当前类名.class);
 *的时候，可以用注解@Slf4j*/
//@RestControllerAdvice /*类似于controller和restController的区别*/
@ControllerAdvice
public class GlobalExceptionHandle {



//    /**
//     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
//     * @param binder
//     */
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        log.info("binder.getFieldDefaultPrefix {}",binder.getFieldDefaultPrefix());
//        log.info("binder.getFieldMarkerPrefix {}",binder.getFieldMarkerPrefix());
//    }



//    /**
//     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
//     * 会先于apiExceptionHandler处理！
//     * @param model
//     */
//    @ModelAttribute
//    public void addAttributes(Model model) {
//        model.addAttribute("msg",rrException);
//    }


    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(RRException.class)
    public String apiExceptionHandler(RRException ex,Model model) {
        log.error("ApiException 异常抛出:", ex);
        /*向前端发送异常信息*/
        model.addAttribute("msg",ex);
        return "/error/500";
    }


}
