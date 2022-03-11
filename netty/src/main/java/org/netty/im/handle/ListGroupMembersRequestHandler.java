package org.netty.im.handle;

import java.util.ArrayList;
import java.util.List;

import org.netty.im.protocol.ListGroupMembersRequestPacket;
import org.netty.im.protocol.ListGroupMembersResponsePacket;
import org.netty.im.protocol.Session;
import org.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * <p>列出群成员处理器</p>
 *
 * @author mengshen
 * @date 2022/3/11 17:49
 */
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
