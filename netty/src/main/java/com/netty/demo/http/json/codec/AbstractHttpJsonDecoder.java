package com.netty.demo.http.json.codec;

import com.common.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHttpJsonDecoder<T> extends MessageToMessageDecoder<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractHttpJsonDecoder.class);


    private Class<?> clazz;
    private boolean isPrint;

    protected AbstractHttpJsonDecoder(Class<?> clazz) {
        this(clazz, false);
    }

    protected AbstractHttpJsonDecoder(Class<?> clazz, boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) {
        String content = body.toString(CharsetUtil.UTF_8);
        if (isPrint) {
            log.debug("The body is : {}", content);
        }
        return JsonUtil.fromJson(content, clazz);
    }
}
