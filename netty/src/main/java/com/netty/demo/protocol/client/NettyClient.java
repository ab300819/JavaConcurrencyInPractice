package com.netty.demo.protocol.client;

import com.netty.demo.protocol.codec.NettyMessageDecoder;
import com.netty.demo.protocol.codec.NettyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 自定义协议客户端
 * {@link <a href="https://www.cnblogs.com/carl10086/p/6195568.html />}
 *
 * @author maosn
 */
public class NettyClient {

    private static final Logger log = LoggerFactory.getLogger(NettyClient.class);
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws Exception {
        new NettyClient().connect("localhost", 9191);
    }

    public void connect(String localhost, int port) throws InterruptedException {
        EventLoopGroup work = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        try {
            b.group(work).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                            ch.pipeline().addLast(new NettyMessageEncoder());
                            ch.pipeline().addLast(new ReadTimeoutHandler(50));
                            ch.pipeline().addLast(new LoginAuthReqHandler());
                            ch.pipeline().addLast(new HeartBeatReqHandler());

                        }
                    });
            ChannelFuture future = b.connect(localhost, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    connect("localhost", 9191);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
