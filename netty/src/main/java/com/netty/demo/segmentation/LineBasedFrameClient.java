package com.netty.demo.segmentation;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用 {@link LineBasedFrameDecoder} 拆包
 *
 * @author maosn
 */
public class LineBasedFrameClient {

    public static void main(String[] args) throws Exception {
        new LineBasedFrameClient().connect(9090, "localhost");
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
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(128));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new LineBasedFrameClientHandler());
                        }

                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    @Slf4j
    public static class LineBasedFrameClientHandler extends SimpleChannelInboundHandler<String> {

        private int counter = 0;
        private byte[] req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ByteBuf message = null;
            for (int i = 0; i < 100; i++) {
                message = Unpooled.buffer(req.length);
                message.writeBytes(req);
                ctx.writeAndFlush(message);
            }
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) {
            log.debug("Now is: {};the count is: {}", msg, ++counter);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
