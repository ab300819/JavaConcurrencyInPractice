package org.netty.im.protocol;

public class SendToGroupResponsePacket extends Packet {

    private String fromGroupId;
    private String msg;
    private Session fromUser;

    public String getFromGroupId() {
        return fromGroupId;
    }

    public void setFromGroupId(String fromGroupId) {
        this.fromGroupId = fromGroupId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Session getFromUser() {
        return fromUser;
    }

    public void setFromUser(Session fromUser) {
        this.fromUser = fromUser;
    }

    @Override
    public byte getCommand() {
        return Command.SEND_TO_GROUP_RESPONSE;
    }
}
