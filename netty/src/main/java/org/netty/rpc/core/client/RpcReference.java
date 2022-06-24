package org.netty.rpc.core.client;

import org.netty.rpc.core.proxy.ProxyFactory;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/23 23:33
 */
public class RpcReference {

    private final ProxyFactory proxyFactory;

    public RpcReference(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    public <T> T get(Class<T> targetClass) {
        return proxyFactory.getProxy(targetClass);
    }


}
