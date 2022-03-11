package org.explore.demo;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <p>模拟 redis 协议</p>
 *
 * @author Mason
 * @date 2021/11/23 20:24
 */
public class NettyRedisServe {

    private static final Logger log = LoggerFactory.getLogger(NettyRedisServe.class);

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        log.info("serve is starting");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {

//                            @Override
//                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                ByteBuf bytebuf = (ByteBuf) msg;
//                                log.info(bytebuf.toString(StandardCharsets.UTF_8));
//
//                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                log.info(msg.toString(StandardCharsets.UTF_8));

                                ByteBuf byteBuf = ctx.alloc().buffer();
                                byteBuf.writeBytes(("Hello World!" + new Date()).getBytes(StandardCharsets.UTF_8));
                                ctx.writeAndFlush(byteBuf);
                            }
                        });
                    }
                }).bind(8090).addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("bind is succeed!");
                    } else {
                        log.info("bind is failed!");
                    }
                });
    }

}
