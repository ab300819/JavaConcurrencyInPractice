package org.common.explore;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/10/22 21:19
 */
public class CglibGen {
    public static void main(String[] args) {
        Programmer programmer = new Programmer();

        Hacker hacker = new Hacker();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(programmer.getClass());
        enhancer.setCallback(hacker);
        Programmer proxy = (Programmer) enhancer.create();
        proxy.code();
    }

    public static class Hacker implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("start cglib");
            methodProxy.invokeSuper(o, objects);
            System.out.println("end cglib");
            return null;
        }
    }

    public static class Programmer {
        public void code() {
            System.out.println("I'm a Programmer, Just Coding...");
        }
    }
}
