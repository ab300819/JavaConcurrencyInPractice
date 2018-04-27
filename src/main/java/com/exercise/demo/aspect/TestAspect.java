package com.exercise.demo.aspect;


import com.exercise.demo.app.service.Food;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

    private static Logger logger = LoggerFactory.getLogger(TestAspect.class);

    @Before("execution(* com.exercise.demo.app.service.Animal.walk())")
    public void silenceCellPhone() {
        logger.debug("Silencing cell phone");
    }

    @Before("execution(* com.exercise.demo.app.service.Animal.walk())")
    public void takeSeats() {
        logger.debug("Take seats");
    }

    @Pointcut("execution(* com.exercise.demo.app.service.Performance.perform(..))")
    public void performance() {
        logger.debug("定义总切点");
    }

    /**
     * 创建环绕通知
     *
     * @param jp
     * @throws Throwable
     */
    @Around("performance()")
    public void watchPerformance(ProceedingJoinPoint jp) throws Throwable {

        logger.debug("Silencing cell phones");
        System.out.println("Taking seats");
        jp.proceed();
        logger.debug("CLAP CLAP CLAP!!!");
    }

    /**
     * 引入新功能
     */
    @DeclareParents(
            value = "com.exercise.demo.app.service.Person+",
            defaultImpl = com.exercise.demo.app.service.FoodImpl.class)
    public static Food food;

}
