package org.netty.rpc.core.proxy;

/**
 * <p>代理工厂借口</p>
 *
 * @author mason
 * @date 2022/6/23 23:35
 */
public interface ProxyFactory {

    <T> T getProxy(Class<T> targetClass);

}
