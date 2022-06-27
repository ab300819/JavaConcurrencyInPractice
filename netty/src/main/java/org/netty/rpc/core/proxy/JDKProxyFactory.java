package org.netty.rpc.core.proxy;

import java.lang.reflect.Proxy;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/23 23:40
 */
public class JDKProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new JDKClientInvocationHandler(clazz));
    }
}
