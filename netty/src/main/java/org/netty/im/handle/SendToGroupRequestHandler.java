package org.netty.im.handle;

import org.netty.im.protocol.SendToGroupRequestPacket;
import org.netty.im.protocol.SendToGroupResponsePacket;
import org.netty.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupRequestPacket msg) throws Exception {
        String groupId = msg.getToGroupId();
        SendToGroupResponsePacket sendToGroupResponsePacket = new SendToGroupResponsePacket();
        sendToGroupResponsePacket.setFromGroupId(msg.getToGroupId());
        sendToGroupResponsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));
        sendToGroupResponsePacket.setMsg(msg.getMsg());

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(sendToGroupResponsePacket);

    }
}
