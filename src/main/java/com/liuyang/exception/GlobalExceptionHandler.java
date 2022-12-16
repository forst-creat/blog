package com.liuyang.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;


/**
 * @author liuyang
 * @create 2022-08-11-0:05
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        log.info("请求路径为 :{},异常是：{}",request.getRequestURL(),e);

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) !=null) {
                throw  e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
