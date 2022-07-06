package org.netty.rpc.core.proxy;

import java.lang.reflect.InvocationTargetException;

import javassist.CannotCompileException;
import javassist.NotFoundException;

/**
 * <p>Javassist 代理工厂</p>
 *
 * @author mason
 * @date 2022/6/26 22:21
 */
public class JavassistProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Class<T> clazz) {
        T result = null;
        try {
            result = (T) ProxyGenerator.newProxyInstance(Thread.currentThread().getContextClassLoader(), clazz, new JavassistInvocationHandler(clazz));
        } catch (CannotCompileException | NotFoundException | NoSuchFieldException | NoSuchMethodException |
                 IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return result;
    }
}
