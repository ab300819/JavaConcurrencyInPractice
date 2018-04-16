package com.exercise.demo.app.service;

import com.exercise.demo.aspect.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestAspect implements Performance {

    private static Logger logger = LoggerFactory.getLogger(TestAspect.class);

    @Override
    public void perform() {
        logger.debug("开始表演！");
    }
}
