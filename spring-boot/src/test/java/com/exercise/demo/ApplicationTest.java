package com.exercise.demo;


import org.junit.Test;

public class ApplicationTest {

    @Test
    public void propertyTest() {
        String property = System.getProperty("java.io.tmpdir");
        System.out.println(property);
    }
}
