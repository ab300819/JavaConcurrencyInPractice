package com.currency.demo.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>数据操作</p>
 *
 * @author mason
 */
public class DataCalculation {

    private final Lock lock = new ReentrantLock();
    private long total = 0;

    public long getTotal() {
        return total;
    }

    public void add() {
        lock.lock();
        try {
            total++;
        } finally {
            lock.unlock();
        }
    }

    public void minus() {
        lock.lock();
        try {
            total--;
        } finally {
            lock.unlock();
        }
    }


}
