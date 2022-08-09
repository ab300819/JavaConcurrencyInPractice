package org.netty.rpc.core.common.event;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/3 01:23
 */
public class MRpcUpdateEvent implements MRpcEvent {

    private Object data;

    public MRpcUpdateEvent(Object data) {
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public MRpcEvent setData(Object data) {
        this.data = data;
        return this;
    }

}
