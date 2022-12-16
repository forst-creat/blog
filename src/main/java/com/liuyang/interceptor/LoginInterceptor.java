package com.liuyang.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录检查
 * 1、配置好拦截器拦截那些请求
 * 2、把这些配置放在容器中
 * @author liuyang
 * @create 2022-08-11-22:29
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     *目标方法执行以前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("拦截的请求路径为{}",requestURI);

        //登录检查逻辑
        HttpSession session = request.getSession();

        Object loginUser = session.getAttribute("loginUser");

        if (loginUser != null){
            //放行
            return true;
        }

        //拦截住。未登录，跳转到登录页
        //   session.setAttribute("msg","请先登录!");
        //   response.sendRedirect("/");
        request.setAttribute("msg","请先登录!");
        request.getRequestDispatcher("/login").forward(request,response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
