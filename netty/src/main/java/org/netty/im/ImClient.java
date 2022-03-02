package org.netty.im;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.netty.im.codec.PacketCodec;
import org.netty.im.protocol.LoginRequestPacket;
import org.netty.im.protocol.LoginResponsePacket;
import org.netty.im.protocol.MessageResponsePacket;
import org.netty.im.protocol.Packet;
import org.netty.im.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
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
                ch.pipeline().addLast(new ClientHandler());
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

    public static class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            PacketCodec codec = new PacketCodec();
            Packet packet = codec.decode(msg);
            if (packet instanceof LoginResponsePacket) {
                LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
                if (loginResponsePacket.isSuccess()) {
                    LoginUtil.markLogin(ctx.channel());
                    log.info("{}: login success", new Date());
                } else {
                    log.info("{}: login failed, the reason is {}", new Date(), loginResponsePacket.getReason());
                }
            } else if (packet instanceof MessageResponsePacket) {
                MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
                log.info(new Date() + ": 收到服务端的信息: " + messageResponsePacket.getMessage());
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("{}: start login", new Date());

            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
            loginRequestPacket.setUserId(UUID.randomUUID().toString());
            loginRequestPacket.setUserName("mason");
            loginRequestPacket.setPassword("123456");

            PacketCodec packetCodec = new PacketCodec();
            ByteBuf byteBuf = packetCodec.encode(ctx.alloc(), loginRequestPacket);

            ctx.writeAndFlush(byteBuf);

        }
    }
}
