package com.demo.controller;

import com.demo.event.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用 contoller
 *
 * @author mason
 */
@RestController
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping("/event")
    public String userRegisterEvent() {
        userService.register("mason");
        return "Hello world";
    }

}
