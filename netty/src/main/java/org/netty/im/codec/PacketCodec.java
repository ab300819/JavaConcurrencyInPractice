package org.netty.im.codec;

import org.netty.im.protocol.Packet;
import org.netty.im.protocol.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * <p>数据包编解码</p>
 *
 * @author mengshen
 * @date 2021/12/9 20:30
 */
public class PacketCodec {

    private static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(Packet packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {

        return null;
    }

}
