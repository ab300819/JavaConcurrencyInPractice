package com.exercise.demo.aspect.configure;


import com.exercise.demo.aspect.component.Food;
import com.exercise.demo.aspect.component.FoodImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Aspect
//@Component
public class AspectConfig {

    private static Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @Before("execution(* com.exercise.demo.aspect.component.Animal.walk(int ))&&args(test)")
    public void silenceCellPhone(int test) {
        logger.debug("test 开始是" + test);
        logger.debug("Silencing cell phone");
    }

    @Before("execution(* com.exercise.demo.aspect.component.Animal.walk())")
    public void takeSeats() {
        logger.debug("Take seats");
    }

    @Pointcut("execution(* com.exercise.demo.aspect.component.Performance.perform(..))")
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
        logger.debug("Taking seats");
        jp.proceed();
        logger.debug("CLAP CLAP CLAP!!!");
    }

    /**
     * 引入新功能
     */
    @DeclareParents(
            value = "com.exercise.demo.aspect.component.Person+",
            defaultImpl = FoodImpl.class)
    public static Food food;

}
