package org.netty.rpc.core.proxy;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/23 23:35
 */
public interface ProxyFactory {

    <T> T getProxy(Class<T> targetClass);

}
