package com.geektime.demo.server;

import com.geektime.demo.common.Operation;
import com.geektime.demo.common.OperationResult;
import com.geektime.demo.common.RequestMessage;
import com.geektime.demo.common.ResponseMessage;
import com.geektime.demo.server.codec.OrderFrameDecoder;
import com.geektime.demo.server.codec.OrderFrameEncoder;
import com.geektime.demo.server.codec.OrderProtocolDecoder;
import com.geektime.demo.server.codec.OrderProtocolEncoder;
import com.geektime.demo.server.handler.AuthHandler;
import com.geektime.demo.server.handler.MetricHandler;
import com.geektime.demo.server.handler.ServerIdleCheckHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import lombok.extern.slf4j.Slf4j;

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

        EventLoopGroup boss = new NioEventLoopGroup(0, new DefaultThreadFactory("boss"));
        EventLoopGroup work = new NioEventLoopGroup(0, new DefaultThreadFactory("work"));
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.group(boss, work);
        serverBootstrap.childOption(NioChannelOption.TCP_NODELAY, true);
        serverBootstrap.option(NioChannelOption.SO_BACKLOG, 1024);


        // 使用线程池处理耗时业务逻辑，适用 IO 密集型
        UnorderedThreadPoolEventExecutor business = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business"));
        // 流量整形
        GlobalTrafficShapingHandler trafficShapingHandler = new GlobalTrafficShapingHandler(new NioEventLoopGroup(), 100 * 1024 * 1024, 100 * 1024 * 1024);
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                // pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                pipeline.addLast("ipFilter", new RuleBasedIpFilter(new IpSubnetFilterRule("127.1.0.1", 16, IpFilterRuleType.REJECT)));
                pipeline.addLast("TShandler", trafficShapingHandler);
                // 空闲检测
                pipeline.addLast("idleHandler", new ServerIdleCheckHandler());
                pipeline.addLast("frameDecoder", new OrderFrameDecoder());
                pipeline.addLast("frameEncoder", new OrderFrameEncoder());
                pipeline.addLast("protocolEncoder", new OrderProtocolEncoder());
                pipeline.addLast("protocolDecoder", new OrderProtocolDecoder());
                pipeline.addLast("metricsHandler", new MetricHandler());

                pipeline.addLast("authHandler", new AuthHandler());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                pipeline.addLast("flushEnhance", new FlushConsolidationHandler(5, true));
                pipeline.addLast(business, new OrderServerHandler());
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

    @Slf4j
    public static class OrderServerHandler extends SimpleChannelInboundHandler<RequestMessage> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) {

            Operation operation = requestMessage.getMessageBody();
            OperationResult operationResult = operation.execute();

            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setMessageHeader(requestMessage.getMessageHeader());
            responseMessage.setMessageBody(operationResult);

            // 设置高低水位
            if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                ctx.writeAndFlush(responseMessage);
            } else {
                log.error("message dropped");
            }
        }

    }

}
