package org.netty.rpc.core.common.event;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/4 01:24
 */
public interface MRpcListener<T> {
    void callback(Object t);
}
