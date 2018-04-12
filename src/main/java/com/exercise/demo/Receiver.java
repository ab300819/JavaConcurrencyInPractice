package com.exercise.demo;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Project: HelloSpring
 * Package: com.exercise.demo
 * Author: mason
 * Time: 17:00
 * Date: 2017-10-14
 * Created with IntelliJ IDEA
 */
@Component
public class Receiver {
    private CountDownLatch latch=new CountDownLatch(1);

    public void receiveMessage(String message){
        System.out.println("Received <"+message+">");
        latch.countDown();
    }
    public CountDownLatch getLatch(){
        return latch;
    }
}
