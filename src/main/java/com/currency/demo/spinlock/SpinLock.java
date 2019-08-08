package com.currency.demo.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 非公平自旋锁
 *
 * @author mason
 */
public class SpinLock {

    private AtomicReference<Thread> cas = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!cas.compareAndSet(null, current)) {
            System.out.println("尝试获取锁");
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        cas.compareAndSet(current, null);
    }
}