package org.netty.im.protocol;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>发送消息到群组请求</p>
 */
@Slf4j
public class SendToGroupRequestPacket extends Packet {

    private String toGroupId;

    private String msg;

    public String getToGroupId() {
        return toGroupId;
    }

    public void setToGroupId(String toGroupId) {
        this.toGroupId = toGroupId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public byte getCommand() {
        return Command.SEND_TO_GROUP_REQUEST;
    }
}
