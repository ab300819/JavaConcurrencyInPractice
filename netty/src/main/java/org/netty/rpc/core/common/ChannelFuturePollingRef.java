package org.netty.rpc.core.common;

import java.util.concurrent.atomic.AtomicLong;

import org.netty.rpc.core.common.cache.CommonClientCache;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/9/4 23:39
 */
public class ChannelFuturePollingRef {

    private final AtomicLong referenceTimes = new AtomicLong(0);

    public ChannelFutureWrapper getChannelFutureWrapper(String serviceName) {
        ChannelFutureWrapper[] channelFutureWrapperArray = CommonClientCache.SERVICE_ROUTER_MAP.get(serviceName);
        long i = referenceTimes.getAndIncrement();
        int index = (int) (i % channelFutureWrapperArray.length);
        return channelFutureWrapperArray[index];
    }

}
