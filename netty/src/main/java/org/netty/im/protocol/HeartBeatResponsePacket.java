package org.netty.im.protocol;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public byte getCommand() {
        return 0;
    }
}
