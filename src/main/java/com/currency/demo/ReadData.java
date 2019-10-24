package com.currency.demo;

import java.io.IOException;
import java.io.PipedInputStream;

public class ReadData {

    public void readData(PipedInputStream in) {
        try {
            byte[] readDataBuffer = new byte[16];
            int readLength=in.read(readDataBuffer);

            for (; readLength != -1; ) {
                String readData = new String(readDataBuffer, 0, readLength);
                System.out.println("read :"+readData);
                readLength = in.read(readDataBuffer);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
