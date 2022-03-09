package org.netty.im.protocol;

public class JoinGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
