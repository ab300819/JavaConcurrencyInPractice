package com.currency.demo;

import java.io.IOException;
import java.io.PipedOutputStream;

public class WriteData {

    public void writeMethod(PipedOutputStream out) {

        try {
            for (int i = 10; i < 500; i++) {
                String outData = String.valueOf(i + 1);
                out.write(outData.getBytes());
                System.out.println("write :"+outData);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




