package com.io.exercise;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class PackageServer {

    public static void main(String[] args) throws Exception {
        new PackageServer().start(9090);
    }

    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        try {
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(128));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class TimeServerHandler extends ChannelInboundHandlerAdapter {

        private static final Logger log = LoggerFactory.getLogger(TimeServerHandler.class);
        private int count = 0;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {


            String result = (String) msg;
            log.debug("Receive message:{}", result);

            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(result) ? new Date().toString() : "BAD ORDER";
            currentTime = currentTime + System.getProperty("line.separator");
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.writeAndFlush(resp);

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
            cause.printStackTrace();
        }
    }

}
