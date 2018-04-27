package com.exercise.demo.app.service;

import org.springframework.stereotype.Service;

/**
 * Project: HelloSpring
 * Package: com.exercise.demo.app.service
 * Author: mason
 * Time: 13:01
 * Date: 2018-04-27
 * Created with IntelliJ IDEA
 */
@Service
public class AnimalImpl implements Animal {
    @Override
    public void walk() {
        System.out.println("动物在走路！！！");
    }
}
