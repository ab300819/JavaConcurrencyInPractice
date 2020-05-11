package com.netty.demo.segmentation;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用 {@link DelimiterBasedFrameDecoder} 拆包
 *
 * @author mason
 */
public class DelimiterBasedFrameClient {

    public static void main(String[] args) throws Exception {
        new DelimiterBasedFrameClient().connect(9090, "localhost");
    }

    public void connect(int port, String host) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameClientHandler());
                        }

                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    @Slf4j
    public static class DelimiterBasedFrameClientHandler extends SimpleChannelInboundHandler<String> {

        private int counter = 0;
        private byte[] req = ("Hi, mason. Welcome to netty.$_").getBytes();

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, String msg) {
            log.debug("This is {} times receive server:{}", ++counter, msg);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            for (int i = 0; i < 100; i++) {
                ctx.writeAndFlush(Unpooled.copiedBuffer(req));
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
        }
    }

}
