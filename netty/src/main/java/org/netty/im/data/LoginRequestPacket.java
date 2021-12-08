package org.netty.im.data;

public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String userName;

    private String password;

    @Override
    public Command getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
