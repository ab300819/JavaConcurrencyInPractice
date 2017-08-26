package com.exercise.demo;

import com.exercise.demo.common.CustomHttpServletRequestWrapper;
import com.exercise.demo.common.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lenovo on 2017/8/17.
 */

@Controller
public class ExternalController {

    private static Logger logger = LoggerFactory.getLogger(ExternalController.class);

    @RequestMapping("/")
    @ResponseBody
    public String ServiceController(HttpServletRequest request, HttpServletResponse response) {

//        response.addHeader("Access-Control-Allow-Origin", "*");  //跨域访问用
        ServletRequest servletRequest = null;
        try {
            servletRequest = new CustomHttpServletRequestWrapper(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("Controller:{}",HttpHelper.getBodyString(servletRequest));


        return "Hello World!";
    }
}
