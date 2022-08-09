package org.netty.rpc.core.common.event.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.netty.rpc.core.client.ConnectionHandler;
import org.netty.rpc.core.common.ChannelFutureWrapper;
import org.netty.rpc.core.common.cache.CommonClientCache;
import org.netty.rpc.core.common.event.MRpcListener;
import org.netty.rpc.core.common.event.MRpcUpdateEvent;
import org.netty.rpc.core.common.event.data.URLChangeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.ChannelFuture;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/6 18:54
 */
public class ServiceUpdateListener implements MRpcListener<MRpcUpdateEvent> {

    private static final Logger log = LoggerFactory.getLogger(ServiceUpdateListener.class);

    @Override
    public void callback(Object t) {
        URLChangeWrapper urlChangeWrapper = (URLChangeWrapper) t;
        List<ChannelFutureWrapper> channelFutureWrapperList = CommonClientCache.CONNECT_MAP.get(urlChangeWrapper.getServiceName());
        if (channelFutureWrapperList == null || channelFutureWrapperList.isEmpty()) {
            log.error("[ServiceUpdateListener] channelFutureWrappers is empty");
        } else {
            List<String> matchProviderUrlList = urlChangeWrapper.getProviderUrl();
            Set<String> finalUrlArray = new HashSet<>();
            List<ChannelFutureWrapper> finalChannelFutureWrapperList = new ArrayList<>();
            for (ChannelFutureWrapper channelFutureWrapper : channelFutureWrapperList) {
                String oldServerAddress = channelFutureWrapper.getHost() + ":" + channelFutureWrapper.getPort();
                if (!matchProviderUrlList.contains(oldServerAddress)) {
                    continue;
                } else {
                    finalChannelFutureWrapperList.add(channelFutureWrapper);
                    finalUrlArray.add(oldServerAddress);
                }
            }

            List<ChannelFutureWrapper> newChannelFutureWrapper = new ArrayList<>();
            for (String newProviderUrl : matchProviderUrlList) {
                if (!finalUrlArray.contains(newProviderUrl)) {
                    ChannelFutureWrapper channelFutureWrapper = new ChannelFutureWrapper();
                    String host = newProviderUrl.split(":")[0];
                    Integer port = Integer.valueOf(newProviderUrl.split(":")[1]);
                    channelFutureWrapper.setHost(host);
                    channelFutureWrapper.setPort(port);
                    ChannelFuture channelFuture = null;

                    try {
                        channelFuture = ConnectionHandler.createChannelFuture(host, port);
                        channelFutureWrapper.setChannelFuture(channelFuture);
                        newChannelFutureWrapper.add(channelFutureWrapper);
                        finalUrlArray.add(newProviderUrl);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            finalChannelFutureWrapperList.addAll(newChannelFutureWrapper);
            CommonClientCache.CONNECT_MAP.put(urlChangeWrapper.getServiceName(), finalChannelFutureWrapperList);
        }
    }
}
