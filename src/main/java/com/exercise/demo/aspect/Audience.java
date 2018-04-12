package com.exercise.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Audience {

    @Before("execution(* com.exercise.demo.aspect.Performance.perform())")
    public void silenceCellPhone() {
        System.out.println("Silencing cell phone");
    }

    @Before("execution(* com.exercise.demo.aspect.Performance.perform())")
    public void takeSeats(){
        System.out.println("Take seats");
    }
}
