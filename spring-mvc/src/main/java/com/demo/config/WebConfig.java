package com.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc 配置
 *
 * @author mason
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.demo.controller"})
public class WebConfig implements WebMvcConfigurer {
}
