package org.netty.rpc.core.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.netty.rpc.core.Selector;
import org.netty.rpc.core.common.ChannelFutureWrapper;
import org.netty.rpc.core.common.cache.CommonClientCache;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/7 21:14
 */
public class ConnectionHandler {

    /**
     * 核心的连接处理器
     * 专门用于负责和服务端构建连接通信
     */
    private static Bootstrap bootstrap;

    public static void setBootstrap(Bootstrap bootstrap) {
        ConnectionHandler.bootstrap = bootstrap;
    }

    public static ChannelFuture getChannelFuture(String providerServiceName) {
        List<ChannelFutureWrapper> channelFutureWrapperList = CommonClientCache.CONNECT_MAP.get(providerServiceName);
        if (channelFutureWrapperList == null || channelFutureWrapperList.isEmpty()) {
            throw new RuntimeException("no provider exist for " + providerServiceName);
        }

        return channelFutureWrapperList.get(new Random().nextInt(channelFutureWrapperList.size())).getChannelFuture();
    }

    public static ChannelFuture createChannelFuture(String ip, Integer port) throws InterruptedException {
        return bootstrap.connect(ip, port).sync();
    }

    public static void connect(String providerServiceName, String providerIp) throws InterruptedException {
        if (bootstrap == null) {
            throw new RuntimeException("bootstrap can not be null");
        }

        if (!providerIp.contains(":")) {
            return;
        }
        String[] providerAddress = providerIp.split(":");
        String ip = providerAddress[0];
        Integer port = Integer.parseInt(providerAddress[1]);

        ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
        ChannelFutureWrapper channelFutureWrapper = new ChannelFutureWrapper();
        channelFutureWrapper.setChannelFuture(channelFuture);
        channelFutureWrapper.setHost(ip);
        channelFutureWrapper.setPort(port);
        CommonClientCache.SERVER_ADDRESS.add(providerIp);
        List<ChannelFutureWrapper> channelFutureWrapperList = CommonClientCache.CONNECT_MAP.get(providerServiceName);
        if (channelFutureWrapperList == null || channelFutureWrapperList.isEmpty()) {
            channelFutureWrapperList = new ArrayList<>();
        }
        channelFutureWrapperList.add(channelFutureWrapper);
        CommonClientCache.CONNECT_MAP.put(providerServiceName, channelFutureWrapperList);

        Selector selector = new Selector();
        selector.setProviderServiceName(providerServiceName);
        CommonClientCache.IROUTER.refreshRouterArray(selector);
    }

    public static void disConnect(String providerServiceName, String providerIp) {
        CommonClientCache.SERVER_ADDRESS.remove(providerIp);
        List<ChannelFutureWrapper> channelFutureWrapperList = CommonClientCache.CONNECT_MAP.get(providerServiceName);
        if (channelFutureWrapperList == null || channelFutureWrapperList.isEmpty()) {
            return;
        }

        Iterator<ChannelFutureWrapper> iterator = channelFutureWrapperList.iterator();
        while (iterator.hasNext()) {
            ChannelFutureWrapper channelFutureWrapper = iterator.next();
            if (providerIp.equals(channelFutureWrapper.getHost() + ":" + channelFutureWrapper.getPort())) {
                iterator.remove();
            }
        }
    }

}
