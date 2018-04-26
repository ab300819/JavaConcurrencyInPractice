package com.exercise.demo.app.service;

import org.springframework.stereotype.Service;

@Service
public class PersonImpl implements Person {

    @Override
    public void speak() {
        System.out.println("我是一个人！！！");
    }
}
