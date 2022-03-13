package org.netty.im.protocol;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public byte getCommand() {
        return 0;
    }
}
