package org.netty.rpc.core.router;

import org.netty.rpc.core.Selector;
import org.netty.rpc.core.common.ChannelFutureWrapper;
import org.netty.rpc.core.registy.Url;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/9/4 23:28
 */
public class RotateRouterImpl implements IRouter {
    @Override
    public void refreshRouterArray(Selector selector) {

    }

    @Override
    public ChannelFutureWrapper select(Selector selector) {
        return null;
    }

    @Override
    public void updateWeight(Url url) {

    }
}
