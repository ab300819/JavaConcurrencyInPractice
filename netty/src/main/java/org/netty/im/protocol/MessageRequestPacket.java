package org.netty.im.protocol;

/**
 * <p>信息体</p>
 *
 * @author mengshen
 * @date 2022/1/6 20:56
 */
public class MessageRequestPacket extends Packet{
    @Override
    public byte getCommand() {
        return 0;
    }
}
