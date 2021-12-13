package org.netty.im;

import org.netty.im.codec.PacketCodec;
import org.netty.im.protocol.LoginRequestPacket;
import org.netty.im.protocol.LoginResponsePacket;
import org.netty.im.protocol.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
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
                            ch.pipeline().addLast(new ServeHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static class ServeHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            log.info("");
            PacketCodec packetCodec = new PacketCodec();

            Packet packet = packetCodec.decode(msg);
            if (packet instanceof LoginRequestPacket) {
                LoginRequestPacket requestPacket = (LoginRequestPacket) packet;

                LoginResponsePacket responsePacket = new LoginResponsePacket();
                if (valid(requestPacket)) {
                    responsePacket.setSuccess(true);
                    log.info("login success");
                } else {
                    responsePacket.setReason("fail to login");
                    responsePacket.setSuccess(false);
                    log.info("login failed");
                }
                ByteBuf responseByteBuf = packetCodec.encode(ctx.alloc(), responsePacket);
                ctx.writeAndFlush(responseByteBuf);
            }

        }

        private boolean valid(LoginRequestPacket packet) {
            return true;
        }
    }

}
