package com.currency.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 添加生产者和消费者队列
 *
 * @author mason
 */
@Slf4j
public class ProducerToConsumer {

    private final int queueSize = 10;
    private final PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ProducerToConsumer producerToConsumer = new ProducerToConsumer();
        Producer producer = producerToConsumer.new Producer();
        Consumer consumer = producerToConsumer.new Consumer();
        executorService.submit(producer);
        executorService.submit(consumer);

    }


    public class Producer implements Runnable {

        private volatile boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.size() == queueSize) {
                        log.info("queue is full");
                        try {
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            flag = false;
                        }
                    }
                    queue.add(1);
                    notEmpty.signal();
                    log.info("insert one element into queue " + (queueSize - queue.size()));
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public class Consumer implements Runnable {

        private volatile boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        log.info("queue is empty");
                        try {
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            flag = false;
                        }
                    }
                    queue.poll();
                    notFull.signal();
                    log.info("take one element from queue " + queue.size());
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
