package com.common.util;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试类加载器
 *
 * @author mason
 */
public class CustomClassLoaderTest {

    @Test
    public void findClassTest() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomClassLoader classLoader = new CustomClassLoader();
        Class<?> cl = Class.forName("Test", true, classLoader);
        Method method = cl.getDeclaredMethod("test", String.class);
        method.invoke(cl.newInstance(), "Hello world!");
    }

}