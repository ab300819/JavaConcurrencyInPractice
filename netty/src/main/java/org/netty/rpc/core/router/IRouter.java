package org.netty.rpc.core.router;

import org.netty.rpc.core.Selector;
import org.netty.rpc.core.common.ChannelFutureWrapper;
import org.netty.rpc.core.registy.Url;

/**
 * <p>路由接口</p>
 *
 * @author mason
 * @date 2022/8/14 11:01
 */
public interface IRouter {

    void refreshRouterArray(Selector selector);

    ChannelFutureWrapper select(Selector selector);

    void updateWeight(Url url);
}
