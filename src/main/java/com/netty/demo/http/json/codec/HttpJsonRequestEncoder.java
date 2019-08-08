package com.netty.demo.http.json.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.net.InetAddress;
import java.util.List;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpJsonRequestEncoder extends AbstractHttpJsonEncoder<HttpJsonRequest> {


    @Override
    protected void encode(ChannelHandlerContext ctx, HttpJsonRequest msg, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, msg.getBody());
        FullHttpRequest request = msg.getRequest();
        if (request == null) {
            request = new DefaultFullHttpRequest(HTTP_1_1, HttpMethod.GET, "/do", body);
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaderNames.HOST, InetAddress.getLocalHost()
                    .getHostAddress());
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaders.Values.CLOSE);
            headers.set(HttpHeaderNames.ACCEPT_ENCODING,
                    HttpHeaderValues.GZIP.toString() + ','
                            + HttpHeaderValues.DEFLATE.toString());
            headers.set(HttpHeaderNames.ACCEPT_CHARSET,
                    "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "zh");
            headers.set(HttpHeaderNames.USER_AGENT,
                    "Netty json Http Client side");
            headers.set(HttpHeaderNames.ACCEPT,
                    "text/html,application/json;q=0.9,*/*;q=0.8");
        }
        HttpUtil.setContentLength(request, body.readableBytes());
        out.add(request);
    }

}
