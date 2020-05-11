package com.currency.demo;

import java.io.PipedOutputStream;

public class WriteThread implements Runnable {

    private WriteData writeData;
    private PipedOutputStream out;

    public WriteThread(WriteData writeData, PipedOutputStream out) {
        this.writeData = writeData;
        this.out = out;
    }

    public void run() {
        writeData.writeMethod(out);
    }
}
