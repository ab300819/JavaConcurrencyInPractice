package org.netty.im.codec;

import java.util.HashMap;
import java.util.Map;

import org.netty.im.protocol.Command;
import org.netty.im.protocol.JsonSerializer;
import org.netty.im.protocol.LoginRequestPacket;
import org.netty.im.protocol.LoginResponsePacket;
import org.netty.im.protocol.MessageRequestPacket;
import org.netty.im.protocol.MessageResponsePacket;
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
    private static final Map<Byte, Class<? extends Packet>> packetMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetMap = new HashMap<>();
        packetMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator,Packet packet) {
        ByteBuf byteBuf = byteBufAllocator.buffer();

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte serializeAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();
        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializerType(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, data);
        }
        return null;
    }

    public Class<? extends Packet> getRequestType(byte command) {
        return packetMap.get(command);
    }

    public Serializer getSerializerType(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

}
