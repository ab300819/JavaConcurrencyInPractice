package com.currency.exercise.spinlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 公平自旋锁
 *
 * @author mason
 */
public class TicketLock {

    /**
     * 服务号
     */
    private AtomicInteger serviceNum = new AtomicInteger();

    /**
     * 排队号
     */
    private AtomicInteger ticketNum = new AtomicInteger();

    /**
     * 用于存储每个线程的排队号
     */
    private ThreadLocal<Integer> ticketNumHolder = new ThreadLocal<>();

    public void lock() {

        int currentTicketNum = ticketNum.incrementAndGet();

        ticketNumHolder.set(currentTicketNum);
        while (currentTicketNum != serviceNum.get()) {

        }

    }

}
