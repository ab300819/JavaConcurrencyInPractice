package com.currency.exercise;

public class AwaitAndSignal implements Runnable {

    private MyService myService;

    public AwaitAndSignal(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run() {
        this.myService.await();
    }
}
