package com.exercise.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
public class Audience {

    private static Logger logger = LoggerFactory.getLogger(Audience.class);

//    @Before("execution(* com.exercise.demo.aspect.Performance.perform())")
//    public void silenceCellPhone() {
//        logger.debug("Silencing cell phone");
//    }
//
//    @Before("execution(* com.exercise.demo.aspect.Performance.perform())")
//    public void takeSeats() {
//        logger.debug("Take seats");
//    }
}
