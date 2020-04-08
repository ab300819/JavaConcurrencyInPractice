package com.netty.demo.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

/**
 * 简单的 http 服务
 *
 * @author mason
 */
public class SimpleHttpServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpServerExpectContinueHandler());
                        pipeline.addLast(new HttpServerHandler());
                    }
                });

        try {
            Channel channel = bootstrap.bind(9191).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

        private static final byte[] CONTENT = "Hello world".getBytes();

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
            if (msg instanceof HttpRequest) {
                HttpRequest httpRequest = (HttpRequest) msg;
                FullHttpResponse httpResponse =
                        new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK, Unpooled.wrappedBuffer(CONTENT));
                httpResponse.headers()
                        .set(CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN)
                        .set(CONTENT_LENGTH, httpResponse.content().readableBytes());
                ctx.writeAndFlush(httpResponse);
            }
        }
    }

}
