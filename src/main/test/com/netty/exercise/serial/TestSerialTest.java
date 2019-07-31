package com.netty.exercise.serial;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class TestSerialTest {

    /**
     * 测试序列号所占空间大小
     *
     * @throws IOException
     */
    @Test
    public void testSerial() throws IOException {
        TestSerial testSerial = new TestSerial();
        testSerial.setTest("mason");
        testSerial.setAge(100);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(testSerial);
        oos.flush();
        oos.close();

        byte[] b = bos.toByteArray();
        System.out.println("The JDK serializable length is :" + b.length);
        bos.close();

        System.out.println("------我是分割线------");
        System.out.println("The byte array serializable length is :" + testSerial.codeC().length);
    }

    /**
     * 测试时间复杂度
     *
     * @throws IOException
     */
    @Test
    public void testPerform() throws IOException {
        TestSerial testSerial = new TestSerial();
        testSerial.setTest("mason");
        testSerial.setAge(100);

        int loop = 1000000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(testSerial);
            oos.flush();
            oos.close();

            byte[] b = bos.toByteArray();
            bos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("The JDK serializable cost time is :" + (endTime - startTime) + "ms");

        System.out.println("------我是分割线------");

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] b = testSerial.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("The byte array serializable cost time is :" + (endTime - startTime) + "ms");
    }

}