package com.exercise.demo.interceptor;

import com.exercise.demo.common.CustomHttpServletRequestWrapper;
import com.exercise.demo.common.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2017/8/22.
 */
//@Component
public class TryInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(TryInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        logger.debug("进入拦截器！");
        ServletRequest servletRequest = new CustomHttpServletRequestWrapper(httpServletRequest);
        String json = HttpHelper.getBodyString(servletRequest);
        logger.debug(json);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
