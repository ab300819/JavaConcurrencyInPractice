package org.netty.rpc.core.proxy;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/26 22:21
 */
public class JavassistProxyFactory implements ProxyFactory{
    @Override
    public <T> T getProxy(Class<T> targetClass) {
        return Proxy;
    }
}
