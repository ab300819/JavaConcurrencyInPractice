package org.explore.demo;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyRedisClient {

    private final static Logger log = LoggerFactory.getLogger(NettyRedisClient.class);

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyRedisClientHandle());

        Channel channel = bootstrap.connect("192.168.52.44", 6380).channel();
        channel.writeAndFlush("*1\r\n$7\r\nCOMMAND\r\n");
    }

    public static class NettyRedisClientHandle extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            log.info("===>"+msg.toString(Charset.defaultCharset()));
        }
    }

}
