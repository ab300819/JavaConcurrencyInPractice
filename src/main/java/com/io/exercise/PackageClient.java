package com.io.exercise;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Date;

public class PackageClient {

    private static final Logger log = LoggerFactory.getLogger(PackageClient.class);

    public static void main(String[] args) throws Exception {
        new PackageClient().connect(9090, "localhost");
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
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }

                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class TimeClientHandler extends ChannelInboundHandlerAdapter {
        private static final Logger log = LoggerFactory.getLogger(TimeClientHandler.class);

        private int counter=0;
        private byte[] req=("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf message=null;
            for (int i = 0; i < 100; i++) {
                message= Unpooled.buffer(req.length);
                message.writeBytes(req);
                ctx.writeAndFlush(message);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf=(ByteBuf)msg;
            byte[] req=new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body=new String(req, CharsetUtil.UTF_8);
            log.debug("Now is: {};the count is:{}",body,++counter);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
