package com.netty.exercise.serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SubReqMarshallingClient {
    private static final Logger log = LoggerFactory.getLogger(SubReqMarshallingClient.class);

    public static void main(String[] args) throws Exception {
        new SubReqMarshallingClient().connect(9090, "localhost");
    }

    public void connect(int port, String host) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();

        try {
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) {
                            sc.pipeline().addLast( MarshallingCodeCFactory.buildMarshallingDecoder());
                            sc.pipeline().addLast( MarshallingCodeCFactory.buildMarshallingEncoder());
                            sc.pipeline().addLast(new SubReqClientHandler());
                        }

                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static class SubReqClientHandler extends ChannelInboundHandlerAdapter {
        private static final Logger log = LoggerFactory.getLogger(SubReqClientHandler.class);

        private static SubscribeReqProto.SubscribeReq subReq(int i) {
            SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
            builder.setSubReqId(i);
            builder.setUserName("Mason");
            builder.setProductName("netty book");

            List<String> address = new ArrayList<>();
            address.add("Nan Jing");
            address.add("Bei Jing");
            address.add("Shen Zheng");
            address.add("Hong Kong");
            builder.addAllAddress(address);
            return builder.build();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            for (int i = 0; i < 10; i++) {
                ctx.write(subReq(i));
            }
            ctx.flush();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            log.debug("Receive server response: [{}]", msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
        }
    }
}
