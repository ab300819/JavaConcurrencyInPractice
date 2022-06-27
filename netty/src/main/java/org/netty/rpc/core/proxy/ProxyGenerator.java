package org.netty.rpc.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.checkerframework.checker.units.qual.C;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/26 22:23
 */
public class ProxyGenerator {

    private static final AtomicInteger counter = new AtomicInteger(1);

    private static ConcurrentHashMap<Class<?>, Object> proxyInstanceCache = new ConcurrentHashMap<>();

    public static Object newProxyInstance(ClassLoader classLoader, Class<?> targetClass, InvocationHandler invocationHandler) {

        if (proxyInstanceCache.containsKey(targetClass)) {
            return proxyInstanceCache.get(targetClass);
        }

        ClassPool pool = ClassPool.getDefault();

        String qualifiedName = generateClassName(targetClass);

        CtClass proxy=pool.makeClass(qualifiedName);

        CtField mf=CtField.make("public static java.lang.reflect.Method[] methods;",proxy)


    }

    private static String generateClassName(Class<?> type) {
        return String.format("%sProxy%d", type.getName(), counter.getAndIncrement());
    }


}
