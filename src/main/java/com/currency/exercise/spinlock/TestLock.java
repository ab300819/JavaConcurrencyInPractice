package com.currency.exercise.spinlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestLock {

    public static void main(String[] args) {
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
