package com.exercise.demo.usercenter.controller;

import com.exercise.demo.common.dto.ReturnResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户中心
 *
 * @author mason
 */
@RestController
@RequestMapping()
public class UserController {

    public ReturnResult login(String userName, String password) {

        return null;
    }

}
