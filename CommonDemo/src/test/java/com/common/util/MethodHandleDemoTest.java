package com.common.util;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class MethodHandleDemoTest {

    @Test
    public void methodHandleTest() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle mh = lookup.findVirtual(MethodHandleDemo.class, "add", methodType);

        int result = (int) mh.invokeExact(new MethodHandleDemo(), 1, 2);
        assertThat(3, equalTo(result));
    }

}