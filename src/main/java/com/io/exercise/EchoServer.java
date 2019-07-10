package com.io.exercise;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoServer {

    public static void main(String[] args) throws Exception {
        new EchoServer().start(9090);
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
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ByteBuf delimiter = Unpooled.copiedBuffer(("$_").getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class EchoServerHandler extends ChannelInboundHandlerAdapter {

        private static final Logger log = LoggerFactory.getLogger(PackageServer.TimeServerHandler.class);
        private int count = 0;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {

            String body = (String) msg;
            log.debug("This is {} times receive client:[{}]", ++count, body);
            body+="$_";
            ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
            ctx.writeAndFlush(echo);

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
            cause.printStackTrace();
        }
    }

}
