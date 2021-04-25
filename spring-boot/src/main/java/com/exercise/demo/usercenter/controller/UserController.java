package com.exercise.demo.usercenter.controller;

import com.exercise.demo.common.dto.ReturnResult;
import com.exercise.demo.mybatis.dao.ImUserDao;
import com.exercise.demo.mybatis.dto.ImUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户中心
 *
 * @author mason
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ImUserDao imUserDao;

    @GetMapping("/login")
    public ReturnResult login(String userName, String password) {
        log.info("hello");
        ImUserDto imUser = imUserDao.selectByPrimaryKey(1);
        log.info(imUser.toString());
        return null;
    }
}
