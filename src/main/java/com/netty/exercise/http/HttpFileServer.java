package com.netty.exercise.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.LOCATION;

public class HttpFileServer {

    private static final String DEFAULT_URL = "/src/main/java/com/netty/exercise/http/";

    public static void main(String[] args) throws Exception {
        new HttpFileServer().start(DEFAULT_URL, 9090);
    }

    public void start(String url, int port) throws Exception {
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
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture f = b.bind("127.0.0.1", port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static class HttpFileServerHandler extends ChannelInboundHandlerAdapter {

        private static final Logger log = LoggerFactory.getLogger(HttpFileServerHandler.class);

        private final String url;

        public HttpFileServerHandler(String url) {
            this.url = url;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {

            FullHttpRequest request = (FullHttpRequest) msg;

            if (!request.decoderResult().isSuccess()) {
                sendError(ctx, HttpResponseStatus.BAD_REQUEST);
                return;
            }

        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
            cause.printStackTrace();
        }

        private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
            response.headers().set(LOCATION, newUri);
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

        private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    status,
                    Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
            response.headers().set(CONTENT_TYPE, "text/plain;charset=UTF-8");
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

        private static void setContentTypeHeader(HttpResponse response, File file) {
            MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
            response.headers().set(CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file.getPath()));
        }
    }

}
