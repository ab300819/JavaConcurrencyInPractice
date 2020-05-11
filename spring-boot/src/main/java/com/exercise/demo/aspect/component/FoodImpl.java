package com.exercise.demo.aspect.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FoodImpl implements Food {

    private static Logger logger = LoggerFactory.getLogger(FoodImpl.class);

    @Override
    public void eat() {

        logger.debug("吃点食物！！！");

    }
}
