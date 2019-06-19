package com.currency.exercise;

import java.io.PipedInputStream;

public class ReadThread implements Runnable {

    private ReadData readData;
    private PipedInputStream in;

    public ReadThread(ReadData readData, PipedInputStream in) {
        this.readData = readData;
        this.in = in;
    }

    public void run() {
        readData.readData(in);
    }
}
