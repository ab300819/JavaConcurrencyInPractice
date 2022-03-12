package org.netty.im.handle;

import org.netty.im.protocol.SendToGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupResponsePacket msg) throws Exception {
        log.info("群[{}]中[{}]发送消息：{}", msg.getFromGroupId(), msg.getFromUser().getUserName(), msg.getMsg());
    }
}
