package org.netty.rpc.core.common.event;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/3 01:21
 */
public interface MRpcEvent {

    Object getData();

    MRpcEvent setData(Object data);

}
