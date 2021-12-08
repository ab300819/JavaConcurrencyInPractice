package org.explore.demo;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Version;

public class NettyRedisClient {

    private final static Logger log = LoggerFactory.getLogger(NettyRedisClient.class);

    public static void main(String[] args) {
        log.info("netty version: {}", Version.identify().entrySet());

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyRedisClientHandle());
                    }
                })
                .connect("127.0.0.1", 8090)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("connect is succeed!");
                    } else {
                        log.info("connect is failed!");
                    }
                });
    }

    public static class NettyRedisClientHandle extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf byteBuf = ctx.alloc().buffer();
            byteBuf.writeBytes(("Hello World!" + new Date()).getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(byteBuf);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            log.info(msg.toString(StandardCharsets.UTF_8));
        }
    }

}
