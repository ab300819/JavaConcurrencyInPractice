package org.netty.im.protocol;

public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
