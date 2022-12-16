package com.liuyang.config;

import com.liuyang.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuyang
 * @create 2022-08-11-22:35
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")//    "/**"也会将静态资源拦截
                .excludePathPatterns("/","/login","/search","/blog/{id}","/css/**","/fonts/**","/images/**","/js/**","/sql","/lib/**");//放行的请求
    }
}
