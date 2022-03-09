package org.netty.im;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.netty.im.codec.PacketDecoder;
import org.netty.im.codec.PacketEncoder;
import org.netty.im.handle.CreateGroupResponseHandler;
import org.netty.im.handle.JoinGroupResponseHandler;
import org.netty.im.handle.LoginResponseHandler;
import org.netty.im.handle.MessageResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ImClient {

    private static final Logger log = LoggerFactory.getLogger(ImClient.class);

    private static final Executor executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup work = new NioEventLoopGroup();
        bootstrap.group(work).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new PacketDecoder());
                ch.pipeline().addLast(new LoginResponseHandler());
                ch.pipeline().addLast(new MessageResponseHandler());
                ch.pipeline().addLast(new CreateGroupResponseHandler());
                ch.pipeline().addLast(new JoinGroupResponseHandler());
                ch.pipeline().addLast(new PacketEncoder());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090).sync()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        executor.execute(new ConsoleInputThread(((ChannelFuture) future).channel()));
                    }
                });
        channelFuture.channel().closeFuture().sync();
    }

}
