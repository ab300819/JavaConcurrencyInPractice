package com.exercise.demo.common.configure;

import com.exercise.demo.parameter.CustomParameterResolve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 自定义 mvc 配置
 *
 * @author mason
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    private CustomParameterResolve argumentParameterResolve;

    @Autowired
    public CustomWebMvcConfigurer(CustomParameterResolve argumentParameterResolve) {
        this.argumentParameterResolve = argumentParameterResolve;
    }

    /**
     * 处理自定义注解参数处理
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(argumentParameterResolve);
    }

    /**
     * 处理跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}