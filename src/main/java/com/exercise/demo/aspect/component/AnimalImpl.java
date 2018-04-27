package com.exercise.demo.aspect.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Project: HelloSpring
 * Package: com.exercise.demo.mybatis.service
 * Author: mason
 * Time: 13:01
 * Date: 2018-04-27
 * Created with IntelliJ IDEA
 */
@Service
public class AnimalImpl implements Animal {

    private static Logger logger = LoggerFactory.getLogger(AnimalImpl.class);

    @Override
    public void walk() {

        logger.debug("动物在走路！！！");

    }
}
