package org.netty.im.protocol;

/**
 * <p>请求信息体</p>
 *
 * @author mason
 * @date 2022/1/6 20:56
 */
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    public MessageRequestPacket() {
    }

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
