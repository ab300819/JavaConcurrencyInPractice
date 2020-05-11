package com.common.util;

import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodHandleDemoTest {

    @Test
    public void methodHandleTest() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle mh = lookup.findVirtual(MethodHandleDemo.class, "add", methodType);

        int result = (int) mh.invokeExact(new MethodHandleDemo(), 1, 2);
        assertEquals(3, result);
    }

}
