package com.exercise.demo.aspect.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonImpl implements Person {

    private static Logger logger = LoggerFactory.getLogger(PersonImpl.class);

    @Override
    public void speak() {
        logger.debug("我是一个人！！！");
    }
}
