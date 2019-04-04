package com.exercise.demo.configuration;

import com.exercise.demo.parameter.ArgumentParameterResolve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    private ArgumentParameterResolve argumentParameterResolve;

    @Autowired
    public MyWebMvcConfigurer(ArgumentParameterResolve argumentParameterResolve) {
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