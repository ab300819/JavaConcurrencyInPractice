package com.netty.exercise.decode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间服务端
 * @author mason
 */
public class TimeServer {

    public static void main(String[] args) throws Exception {
        new TimeServer().start(9090);
    }

    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        try {
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    /**
                     * 对应的是tcp/ip协议listen函数中的backlog参数，
                     * 函数listen(int socketfd,int backlog)用来初始化服务端可连接队列，
                     * 服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，
                     * 多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                     *
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    /**
                     * 心跳检测
                     */
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class TimeServerHandler extends ChannelInboundHandlerAdapter {

        private static final Logger log = LoggerFactory.getLogger(TimeServerHandler.class);

        @Override
        public void channelActive(ChannelHandlerContext ctx) {

            final ByteBuf time = ctx.alloc().buffer(4);
            time.writeInt((int) (System.currentTimeMillis() / 1000L));

            final ChannelFuture f = ctx.writeAndFlush(time);
            f.addListener((ChannelFutureListener) future -> {
                ctx.close();
                log.info("ChannelHandlerContext closed");
            });
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
            cause.printStackTrace();
        }
    }

}
