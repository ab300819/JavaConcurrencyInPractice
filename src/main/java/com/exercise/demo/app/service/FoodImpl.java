package com.exercise.demo.app.service;

import org.springframework.stereotype.Service;

@Service
public class FoodImpl implements Food {
    @Override
    public void eat() {
        System.out.println("吃点食物！！！");
    }
}
