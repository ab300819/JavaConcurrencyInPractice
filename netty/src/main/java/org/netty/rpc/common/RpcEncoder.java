package org.netty.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/4/18 21:56
 */
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getMagicNumber());
        out.writeByte(msg.getContentLength());
        out.writeBytes(msg.getContent());
    }
}
