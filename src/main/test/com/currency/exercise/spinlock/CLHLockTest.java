package com.currency.exercise.spinlock;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

public class CLHLockTest {

    @Test
    public void clhlockTest() {
        final CLHLock lock = new CLHLock();
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "获取锁");
                lock.lock();
                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + "释放锁");
                }
            });
        }
    }

}