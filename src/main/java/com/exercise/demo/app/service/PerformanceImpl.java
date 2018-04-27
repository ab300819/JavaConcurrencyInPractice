package com.exercise.demo.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PerformanceImpl implements Performance {

    private static Logger logger = LoggerFactory.getLogger(PerformanceImpl.class);

    @Override
    public void perform() {
        logger.debug("开始表演！");
    }
}
