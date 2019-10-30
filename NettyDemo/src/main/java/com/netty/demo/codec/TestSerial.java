package com.netty.demo.codec;

import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.nio.ByteBuffer;

@Message
public class TestSerial implements Serializable {

    private static final long serialVersionUID = 6426785127985794642L;

    private int age;

    private String test;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public byte[] codeC() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.test.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.age);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte[] codeC(ByteBuffer buffer) {
        buffer.clear();
        byte[] value = this.test.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.age);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
