package org.netty.im.protocol;

/**
 * <p>列出群聊成员请求</p>
 *
 * @author mengshen
 * @date 2022/3/11 16:26
 */
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
