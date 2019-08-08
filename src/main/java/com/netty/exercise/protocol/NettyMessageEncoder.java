package com.netty.exercise.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.Map;

public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {

    private MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf senBuf) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }

        senBuf.writeInt(msg.getHeader().getCrcCode());
        senBuf.writeInt(msg.getHeader().getLength());
        senBuf.writeLong(msg.getHeader().getSessionID());
        senBuf.writeByte(msg.getHeader().getType());
        senBuf.writeByte(msg.getHeader().getPriority());
        senBuf.writeInt(msg.getHeader().getAttachment().size());

        String key;
        byte[] keyArray;
        Object value;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes(CharsetUtil.UTF_8);
            senBuf.writeInt(keyArray.length);
            senBuf.writeBytes(keyArray);
            value = param.getValue();
            marshallingEncoder.encode(value, senBuf);
        }

        if (msg.getBody() != null) {
            marshallingEncoder.encode(msg.getBody(), senBuf);
        } else {
            senBuf.writeInt(0);
        }
        senBuf.setInt(4, senBuf.readableBytes());

    }
}
