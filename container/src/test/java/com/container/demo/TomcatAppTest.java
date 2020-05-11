package com.container.demo;

import org.junit.Test;

import java.util.Map;
import java.util.Properties;

public class TomcatAppTest {

    @Test
    public void propertyTest() {
        Properties properties = System.getProperties();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
