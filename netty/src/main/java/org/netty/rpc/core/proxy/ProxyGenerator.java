package org.netty.rpc.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.NotFoundException;

/**
 * <p>使用javassist来生成字节码</p>
 *
 * @author mason
 * @date 2022/6/26 22:23
 */
public class ProxyGenerator {

    private static final AtomicInteger counter = new AtomicInteger(1);

    private static ConcurrentHashMap<Class<?>, Object> proxyInstanceCache = new ConcurrentHashMap<>();

    public static Object newProxyInstance(ClassLoader classLoader, Class<?> targetClass, InvocationHandler invocationHandler) throws CannotCompileException, NotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        if (proxyInstanceCache.containsKey(targetClass)) {
            return proxyInstanceCache.get(targetClass);
        }

        ClassPool pool = ClassPool.getDefault();

        String qualifiedName = generateClassName(targetClass);

        CtClass proxy = pool.makeClass(qualifiedName);

        CtField mf = CtField.make("public static java.lang.reflect.Method[] methods;", proxy);
        proxy.addField(mf);

        CtField hf = CtField.make("private " + InvocationHandler.class.getName() + " handler;", proxy);
        proxy.addField(hf);

        CtConstructor constructor = new CtConstructor(new CtClass[]{pool.get(InvocationHandler.class.getName())}, proxy);
        constructor.setBody("this.handler=$1;");
        constructor.setModifiers(Modifier.PUBLIC);
        proxy.addConstructor(constructor);

        proxy.addConstructor(CtNewConstructor.defaultConstructor(proxy));
        targetClass.getInterfaces();

        List<Method> methods = new ArrayList<>();
        CtClass ctClass = pool.get(targetClass.getName());

        proxy.addInterface(ctClass);

        Method[] arr = targetClass.getDeclaredMethods();
        int ix = methods.size();
        for (Method method : arr) {
            Class<?> rt = method.getReturnType();
            Class<?>[] pts = method.getParameterTypes();

            StringBuilder code = new StringBuilder("Object[] args=new Object[").append(pts.length).append("];");
            for (int j = 0; j < pts.length; j++) {
                code.append(" args[").append(j).append("] = ($w)$").append(j + 1).append(";");
            }

            code.append(" Object ret = handler.invoke(this, methods[" + ix + "], args);");
            if (!Void.TYPE.equals(rt)) {
                code.append(" return ").append(asArgument(rt, "ret")).append(";");
            }

            StringBuilder sb = new StringBuilder(1024);
            sb.append(modifier(method.getModifiers())).append(' ').append(getParameterType(rt)).append(' ')
                    .append(method.getName()).append("(");

            Class<?>[] ets = method.getExceptionTypes();
            if (ets != null && ets.length > 0) {
                sb.append(" throws ");
                for (int i = 0; i < ets.length; i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(getParameterType(ets[i]));
                }
            }
            sb.append('{').append(code.toString()).append('}');

            CtMethod ctMethod = CtMethod.make(sb.toString(), proxy);
            proxy.addMethod(ctMethod);

            methods.add(method);
        }

        proxy.setModifiers(Modifier.PUBLIC);

        Class<?> proxyClass = proxy.toClass(classLoader, null);
        proxyClass.getField("methods").set(null, methods.toArray(new Method[0]));

        Object instance = proxyClass.getConstructor(InvocationHandler.class).newInstance(invocationHandler);
        Object old = proxyInstanceCache.putIfAbsent(targetClass, instance);
        if (old != null) {
            instance = old;
        }

        return instance;
    }

    private static String modifier(int mod) {

        return null;
    }

    public static String getParameterType(Class<?> clazz) {

        return null;
    }

    private static String asArgument(Class<?> cl, String name) {

        return null;
    }

    private static String generateClassName(Class<?> type) {
        return String.format("%sProxy%d", type.getName(), counter.getAndIncrement());
    }


}
