package org.netty.im.protocol;

public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
