package org.netty.im.codec;

import org.netty.im.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <p>添加 packet 编码器</p>
 *
 * @author mason
 * @date 2022/3/4 11:33
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodec codec = new PacketCodec();
        codec.encode(out,msg);
    }
}
