package com.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 自定义类加载器
 *
 * @author mason
 * @see ClassLoader#findClass(String) 不破坏双亲委派模型
 * @see ClassLoader#loadClass(String)  破坏双亲委派模型
 */
public class CustomClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = getClassFile(name);

        try {
            byte[] bytes = getClassBytes(file);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private File getClassFile(String name) {
        return new File("Test.class");

    }

    private byte[] getClassBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel wbc = Channels.newChannel(baos);
        ByteBuffer by = ByteBuffer.allocate(1024);


        while (fc.read(by) > 0) {
            by.flip();
            wbc.write(by);
            by.clear();
        }

        fis.close();
        return baos.toByteArray();
    }
}
