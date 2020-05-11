package com.netty.demo.http.json.server;

import com.netty.demo.http.json.codec.HttpJsonRequest;
import com.netty.demo.http.json.codec.HttpJsonRequestDecoder;
import com.netty.demo.http.json.codec.HttpJsonResponse;
import com.netty.demo.http.json.codec.HttpJsonResponseEncoder;
import com.netty.demo.http.json.dto.Address;
import com.netty.demo.http.json.dto.Order;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static io.netty.channel.ChannelFutureListener.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpJsonServer {

    public static void main(String[] args) throws Exception {
        new HttpJsonServer().start(9191);
    }

    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        try {
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            ch.pipeline().addLast(new HttpJsonRequestDecoder(Order.class, true));
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new HttpJsonResponseEncoder());
                            ch.pipeline().addLast(new HttpJsonServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class HttpJsonServerHandler extends SimpleChannelInboundHandler<HttpJsonRequest> {

        private static final Logger log = LoggerFactory.getLogger(HttpJsonServerHandler.class);

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, HttpJsonRequest msg) throws Exception {
            HttpRequest request = msg.getRequest();
            Order order = (Order) msg.getBody();
            log.debug("Http server receive request:{} ", order);
            doBusiness(order);
            ChannelFuture future = ctx.writeAndFlush(new HttpJsonResponse(null, order));
            if (!HttpUtil.isKeepAlive(request)) {
                future.addListener(future1 -> ctx.close());
            }
        }

        private void doBusiness(Order order) {
            order.getCustomer().setFirstName("狄");
            order.getCustomer().setLastName("仁杰");
            List<String> midNames = new ArrayList<String>();
            midNames.add("李元芳");
            order.getCustomer().setMiddleNames(midNames);
            Address address = order.getBillTo();
            address.setCity("洛阳");
            address.setCountry("大唐");
            address.setState("河南道");
            address.setPostCode("123456");
            order.setBillTo(address);
            order.setShipTo(address);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            if (ctx.channel().isActive()) {
                sendError(ctx, INTERNAL_SERVER_ERROR);
            }
        }

        private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
            response.headers().set(CONTENT_TYPE, "text/plain;charset=UTF-8");
            ctx.writeAndFlush(response).addListener(CLOSE);
        }

    }

}
