package org.netty.im.protocol;

/**
 * <p>响应信息体</p>
 *
 * @author mason
 */
public class MessageResponsePacket extends Packet {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
