package org.netty.im.protocol;

import java.util.List;

/**
 * <p>列出群组成员返回</p>
 *
 * @author mengshen
 * @date 2022/3/11 17:56
 */
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    @Override
    public byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
