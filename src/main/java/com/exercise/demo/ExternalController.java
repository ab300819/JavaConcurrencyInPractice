package com.exercise.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2017/8/17.
 */

@RestController
public class ExternalController {

    @RequestMapping("/")
    @ResponseBody
    public String ServiceController() {


        return "Hello World!";
    }
}
