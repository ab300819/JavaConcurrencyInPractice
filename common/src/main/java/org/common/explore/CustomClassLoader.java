package org.common.explore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 自定义类加载器
 * </p>
 *
 * @author mason
 * @date 2022/9/29 01:17
 */
public class CustomClassLoader extends ClassLoader {

    private static final Logger log = LoggerFactory.getLogger(CustomClassLoader.class);

    public Class<?> defineMyClass(byte[] b, int off, int len) {
        return super.defineClass(null, b, off, len);
    }

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        InputStream inputStream = new FileInputStream("/Users/mason/Project/playground/lang/java_test/Programmer.class");
        byte[] result = new byte[1024];

        int count = inputStream.read(result);

        CustomClassLoader loader = new CustomClassLoader();
        Class clazz = loader.defineMyClass(result, 0, count);

        System.out.println(clazz.getCanonicalName());

        Object o = clazz.newInstance();
        clazz.getMethod("code", null).invoke(o, null);
    }

}
