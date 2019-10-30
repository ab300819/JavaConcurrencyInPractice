package com.netty.demo.http.json.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpJsonResponseDecoder extends AbstractHttpJsonDecoder<FullHttpResponse> {

    private static final Logger log = LoggerFactory.getLogger(HttpJsonResponseDecoder.class);

    protected HttpJsonResponseDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    public HttpJsonResponseDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
        log.debug("开始解码...");
        out.add(new HttpJsonResponse(msg, decode0(ctx, msg.content())));
    }
}
