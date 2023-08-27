package org.netty.rpc.core.common;

import io.netty.channel.ChannelFuture;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/7 01:06
 */
public class ChannelFutureWrapper {

    private ChannelFuture channelFuture;
    private String host;
    private Integer port;
    private Integer weight;

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
