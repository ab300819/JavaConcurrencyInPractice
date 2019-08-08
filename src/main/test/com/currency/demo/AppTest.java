package com.currency.demo;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class AppTest {

    @Test
    public void BaseClassTest(){
        AtomicInteger atomicInteger=new AtomicInteger();
        System.out.println(atomicInteger.get());
        atomicInteger.incrementAndGet();
        System.out.println(atomicInteger.get());

        atomicInteger.getAndSet(3);
        System.out.println(atomicInteger.get());
    }

}