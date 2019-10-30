package com.currency.demo;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {

        WriteData writeData=new WriteData();
        ReadData readData=new ReadData();

        PipedInputStream inputStream=new PipedInputStream();
        PipedOutputStream outputStream=new PipedOutputStream();

        outputStream.connect(inputStream);

        ReadThread readThread=new ReadThread(readData,inputStream);
        new Thread(readThread).start();

        Thread.sleep(2000);

        WriteThread writeThread=new WriteThread(writeData,outputStream);
        new Thread(writeThread).start();

        MyService myService=new MyService();
        new Thread(new AwaitAndSignal(myService)).start();
        Thread.sleep(5000);
        myService.signal();

    }
}
