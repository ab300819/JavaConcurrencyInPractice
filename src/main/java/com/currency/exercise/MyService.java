package com.currency.exercise;

import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {

    private Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public void await() {

        lock.lock();
        try {

            System.out.println("await 时间为" + new Date());
            condition.await();
            System.out.println("await() 方法之后的语句，signal() 方法之后才被执行");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void signal(){
        lock.lock();
        try {
            System.out.println("signal 时间为"+new Date());
            condition.signal();
            Thread.sleep(3000);
            System.out.println("这是 signal 方法之后的语句");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
