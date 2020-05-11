package com.geektime.demo.server.codec;

import com.geektime.demo.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.decode(msg);
        out.add(requestMessage);
    }

}
