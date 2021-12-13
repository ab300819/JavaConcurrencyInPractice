package org.netty.im.protocol;

public class LoginResponsePacket extends Packet {
    @Override
    public byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
