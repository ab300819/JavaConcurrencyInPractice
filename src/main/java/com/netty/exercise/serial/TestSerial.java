package com.netty.exercise.serial;

import org.msgpack.annotation.Message;

@Message
public class TestSerial {

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
}
