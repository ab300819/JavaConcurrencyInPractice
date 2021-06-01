package com.currency.demo.jdk;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinusThread implements Runnable {

    private final int times;
    private final DataCalculation dataCalculation;

    public MinusThread(int times, DataCalculation dataCalculation) {
        this.dataCalculation = dataCalculation;
        this.times = times;
    }

    @Override
    public void run() {
        for (int i = times; i > 0; --i) {
            dataCalculation.minus();
            log.info("current thread name: {}. The value is {}", Thread.currentThread().getName(), dataCalculation.getTotal());
        }
    }
}
