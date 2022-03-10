package org.netty.im.handle;

import org.netty.im.protocol.QuitGroupRequestPacket;
import org.netty.im.protocol.QuitGroupResponsePacket;
import org.netty.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(msg.getGroupId());
        ctx.channel().writeAndFlush(responsePacket);

    }

}
