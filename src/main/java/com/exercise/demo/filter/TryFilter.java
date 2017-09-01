package com.exercise.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by lenovo on 2017/8/17.
 */
//@WebFilter(filterName = "TryFilter", urlPatterns = "/*")
public class TryFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(TryFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.debug("执行过滤操作");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.debug("过滤器销毁");
    }
}
