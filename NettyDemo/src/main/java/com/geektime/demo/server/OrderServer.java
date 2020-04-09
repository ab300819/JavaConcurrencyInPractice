package com.geektime.demo.server;

import com.geektime.demo.common.Operation;
import com.geektime.demo.common.OperationResult;
import com.geektime.demo.common.RequestMessage;
import com.geektime.demo.common.ResponseMessage;
import com.geektime.demo.server.codec.OrderFrameDecoder;
import com.geektime.demo.server.codec.OrderFrameEncoder;
import com.geektime.demo.server.codec.OrderProtocolDecoder;
import com.geektime.demo.server.codec.OrderProtocolEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Order 服务端
 *
 * @author mason
 */
public class OrderServer {

    public static void main(String[] args) throws InterruptedException {
        OrderServer.start(9090);
    }

    public static void start(int port) throws InterruptedException {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.group(boss, work);

        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderServerHandler());
            }
        });

        ChannelFuture channelFuture;
        try {
            channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

    public static class OrderServerHandler extends SimpleChannelInboundHandler<RequestMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) {

            Operation operation = requestMessage.getMessageBody();
            OperationResult operationResult = operation.execute();

            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setMessageHeader(requestMessage.getMessageHeader());
            responseMessage.setMessageBody(operationResult);

            ctx.writeAndFlush(responseMessage);
        }
    }

}
