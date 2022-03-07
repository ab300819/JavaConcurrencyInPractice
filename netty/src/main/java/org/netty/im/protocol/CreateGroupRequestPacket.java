package org.netty.im.protocol;

import java.util.List;

public class CreateGroupRequestPacket extends Packet{

    private List<String> userIdList;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
