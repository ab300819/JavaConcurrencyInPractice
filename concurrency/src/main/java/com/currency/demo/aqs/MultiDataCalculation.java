package com.currency.demo.aqs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiDataCalculation {

    public static void main(String[] args) {
        DataCalculation dataCalculation = new DataCalculation();
        for (int i = 0; i < 10; i++) {
            AddTread addTread = new AddTread(100000, dataCalculation);
            Thread thread = new Thread(addTread);
            thread.start();
        }
        log.info("the last count is {}", dataCalculation.getTotal());
        for (int i = 0; i < 10; i++) {
            MinusThread minusThread = new MinusThread(100000, dataCalculation);
            Thread thread = new Thread(minusThread);
            thread.start();
        }
        log.info("the last count is {}", dataCalculation.getTotal());
    }

}
