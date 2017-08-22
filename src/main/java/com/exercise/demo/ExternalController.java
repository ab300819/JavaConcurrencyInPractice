package com.exercise.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2017/8/17.
 */

@Controller
public class ExternalController {

    @RequestMapping("/")
    @ResponseBody
    public String ServiceController(@RequestBody String params,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {

        response.addHeader("Access-Control-Allow-Origin", "*");  //跨域访问用
        System.out.println(params);

        return "{\"test\":\"123\"}";
    }
}
