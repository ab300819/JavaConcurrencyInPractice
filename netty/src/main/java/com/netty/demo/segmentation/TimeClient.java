package com.netty.demo.segmentation;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 时间客户端
 *
 * @author mason
 */
public class TimeClient {

    private static final Logger log = LoggerFactory.getLogger(TimeClient.class);

    public static void main(String[] args) throws Exception {
        new TimeClient().connect(9090, "localhost");
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
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }

                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class TimeClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
        private static final Logger log = LoggerFactory.getLogger(TimeClientHandler.class);

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
            try {
                long currentMills = msg.readUnsignedInt() * 1000L;
                log.info("server time {}", new Date(currentMills));
                ctx.close();
            } finally {
                msg.release();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
            cause.printStackTrace();
        }
    }
}
