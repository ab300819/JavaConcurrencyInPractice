package com.netty.demo.segmentation;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 使用 {@link LineBasedFrameDecoder} 拆包
 *
 * @author maosn
 */
public class LineBasedFrameServer {

    public static void main(String[] args) throws Exception {
        new LineBasedFrameServer().start(9090);
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
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {

                            ch.pipeline().addLast(new LineBasedFrameDecoder(128));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new LineBasedFrameServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Slf4j
    public static class LineBasedFrameServerHandler extends SimpleChannelInboundHandler<String> {

        private int count = 0;

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) {
            log.debug("Receive message:{}", msg);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(msg) ? new Date().toString() : "BAD ORDER";
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
