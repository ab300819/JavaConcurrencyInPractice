package com.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyDemo {

    public static void main(String[] args) {
        InvocationHandler handler = (proxy, method, args1) -> {
            System.out.println(method.getName());
            if (method.getName().equals("sayHello")) {
                System.out.println("Hello World!");
            }
            return null;
        };

        Say say = (Say) Proxy.newProxyInstance(Say.class.getClassLoader(), new Class[]{Say.class}, handler);
        say.sayHello();
    }

    interface Say {
        void sayHello();
    }

}
