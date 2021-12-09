package com.netty.demo.http.json.codec;

import com.common.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

public abstract class AbstractHttpJsonEncoder<T> extends MessageToMessageEncoder<T> {

    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) {
        String jsonStr = JsonUtil.toString(body);
        return Unpooled.copiedBuffer(jsonStr, CharsetUtil.UTF_8);
    }

}
