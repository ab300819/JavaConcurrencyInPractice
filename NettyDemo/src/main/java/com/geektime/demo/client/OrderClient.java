package com.geektime.demo.client;

import com.common.util.IdUtil;
import com.geektime.demo.client.codec.*;
import com.geektime.demo.client.dispatcher.OperationResultFuture;
import com.geektime.demo.client.dispatcher.RequestPendingCenter;
import com.geektime.demo.common.OperationResult;
import com.geektime.demo.common.RequestMessage;
import com.geektime.demo.common.ResponseMessage;
import com.geektime.demo.common.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;


public class OrderClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        OrderClient.connect("localhost", 9090);
    }

    public static void connect(String address, int port) throws InterruptedException, ExecutionException {

        EventLoopGroup work = new NioEventLoopGroup();
        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

        Bootstrap group = new Bootstrap();
        group.channel(NioSocketChannel.class);
        group.group(work);

        group.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());

                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());

                pipeline.addLast(new OrderClientHandler(requestPendingCenter));

                pipeline.addLast(new OperationToRequestMessageEncoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = group.connect(address, port);
        try {
            channelFuture.sync();
            long streamId = IdUtil.nextId();
            RequestMessage requestMessage = new RequestMessage(streamId, new OrderOperation(1001, "tudou"));

            OperationResultFuture operationResultFuture = new OperationResultFuture();
            requestPendingCenter.add(streamId, operationResultFuture);
            channelFuture.channel().writeAndFlush(requestMessage);
            OperationResult operationResult = operationResultFuture.get();
            System.out.println(operationResult);
            channelFuture.channel().closeFuture().sync();
        } finally {
            work.shutdownGracefully();
        }
    }

    public static class OrderClientHandler extends SimpleChannelInboundHandler<ResponseMessage> {

        private RequestPendingCenter requestPendingCenter;

        public OrderClientHandler(RequestPendingCenter requestPendingCenter) {
            this.requestPendingCenter = requestPendingCenter;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ResponseMessage msg) {
            requestPendingCenter.set(msg.getMessageHeader().getStreamId(), msg.getMessageBody());
        }
    }

}
