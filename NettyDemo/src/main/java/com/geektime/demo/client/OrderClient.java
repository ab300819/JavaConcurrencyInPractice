package com.geektime.demo.client;

import com.common.util.IdUtil;
import com.geektime.demo.client.codec.*;
import com.geektime.demo.client.dispatcher.OperationResultFuture;
import com.geektime.demo.client.dispatcher.RequestPendingCenter;
import com.geektime.demo.common.OperationResult;
import com.geektime.demo.common.RequestMessage;
import com.geektime.demo.common.ResponseMessage;
import com.geektime.demo.common.auth.AuthOperation;
import com.geektime.demo.common.order.OrderOperation;
import com.geektime.demo.server.handler.ClientIdleCheckHandler;
import com.geektime.demo.server.handler.KeepaliveHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;

@Slf4j
public class OrderClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException, CertificateException, SSLException {
        OrderClient.connect("localhost", 9090);
    }

    public static void connect(String address, int port) throws InterruptedException, ExecutionException, SSLException {

        EventLoopGroup work = new NioEventLoopGroup();
        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

        Bootstrap group = new Bootstrap();
        group.channel(NioSocketChannel.class);
        group.group(work);

        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        group.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast(new ClientIdleCheckHandler());

                pipeline.addLast(sslContext.newHandler(ch.alloc()));
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());

                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());

                pipeline.addLast(new OrderClientHandler(requestPendingCenter));

                pipeline.addLast(new OperationToRequestMessageEncoder());
                pipeline.addLast(new KeepaliveHandler());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = group.connect(address, port);
        try {
            channelFuture.sync();
            RequestMessage authMessage = new RequestMessage(IdUtil.nextId(), new AuthOperation("admin", "password"));
            channelFuture.channel().writeAndFlush(authMessage);

            long streamId = IdUtil.nextId();
            RequestMessage requestMessage = new RequestMessage(streamId, new OrderOperation(1001, "tudou"));

            OperationResultFuture operationResultFuture = new OperationResultFuture();
            requestPendingCenter.add(streamId, operationResultFuture);
            channelFuture.channel().writeAndFlush(requestMessage);
            OperationResult operationResult = operationResultFuture.get();
            log.info(operationResult.toString());
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
