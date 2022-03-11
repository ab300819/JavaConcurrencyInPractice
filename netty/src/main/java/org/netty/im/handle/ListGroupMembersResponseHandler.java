package org.netty.im.handle;

import org.netty.im.protocol.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>列出群成员处理器</p>
 *
 * @author mengshen
 * @date 2022/3/11 17:49
 */
@Slf4j
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        log.info("群[{}]中成员包括：{}", msg.getGroupId(), msg.getSessionList());
    }
}
