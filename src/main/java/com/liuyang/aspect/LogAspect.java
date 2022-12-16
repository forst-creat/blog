package com.liuyang.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuyang
 * @create 2022-08-11-1:35
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.liuyang.web.admin.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
       // logger.info("------------doAfter---------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result: {}",result);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
