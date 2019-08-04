package com.netty.exercise.http;

import com.netty.exercise.http.dto.Order;
import com.netty.exercise.http.dto.OrderFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class HttpJsonClient {

    public static void main(String[] args) throws Exception {
        new HttpJsonClient().start("localhost", 9191);
    }

    public void start(String host, int port) throws Exception {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        try {
            b.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new HttpResponseDecoder());
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            ch.pipeline().addLast(new HttpJsonResponseDecoder(Order.class, true));
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            ch.pipeline().addLast(new HttpJsonRequestEncoder());
                            ch.pipeline().addLast(new HttpJsonClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();
            f.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }
    }

    public static class HttpJsonClientHandler extends SimpleChannelInboundHandler<HttpJsonResponse> {

        private static final Logger log = LoggerFactory.getLogger(HttpJsonClientHandler.class);

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.debug("connect to server");
            HttpJsonRequest request = new HttpJsonRequest(null, OrderFactory.create(999));
            ctx.writeAndFlush(request);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, HttpJsonResponse msg) throws Exception {
            log.debug(msg.getClass().getName());
            log.debug("receive data:{}", msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
