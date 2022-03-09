package org.netty.im.handle;

import org.netty.im.protocol.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            log.info("加入群[{}]成功！", msg.getGroupId());
        } else {
            log.info("加入群[{}]失败！", msg.getGroupId());
        }
    }
}
