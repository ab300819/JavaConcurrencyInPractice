package org.netty.im.protocol;

public class QuitGroupRequestPacket extends Packet{

    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
