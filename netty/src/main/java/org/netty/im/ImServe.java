package org.netty.im;

import org.netty.im.codec.FrameCodec;
import org.netty.im.codec.PacketDecoder;
import org.netty.im.codec.PacketEncoder;
import org.netty.im.handle.AuthHandler;
import org.netty.im.handle.LifeCycleHandler;
import org.netty.im.handle.LoginRequestHandler;
import org.netty.im.handle.MessageRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ImServe {

    private static final Logger log = LoggerFactory.getLogger(ImServe.class);

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        try {
            serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new FrameCodec());
                            ch.pipeline().addLast(new InBoundHandlerA());
                            ch.pipeline().addLast(new InBoundHandlerB());
                            ch.pipeline().addLast(new InBoundHandlerC());
                            ch.pipeline().addLast(new LifeCycleHandler());
                            ch.pipeline().addLast(new PacketDecoder());
                            ch.pipeline().addLast(new LoginRequestHandler());
                            ch.pipeline().addLast(new MessageRequestHandler());

                            ch.pipeline().addLast(new PacketEncoder());
                            ch.pipeline().addLast(new OutBoundHandlerC());
                            ch.pipeline().addLast(new OutBoundHandlerB());
                            ch.pipeline().addLast(new OutBoundHandlerA());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(8090).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static class InBoundHandlerA extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("==> A");
            super.channelRead(ctx, msg);
        }
    }

    public static class InBoundHandlerB extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("==> B");
            super.channelRead(ctx, msg);
        }
    }

    public static class InBoundHandlerC extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("==> C");
            super.channelRead(ctx, msg);
        }
    }

    public static class OutBoundHandlerA extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("<== A");
            super.write(ctx, msg, promise);
        }
    }

    public static class OutBoundHandlerB extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("<== B");
            super.write(ctx, msg, promise);
        }
    }

    public static class OutBoundHandlerC extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("<== C");
            super.write(ctx, msg, promise);
        }
    }

}
