package org.netty.im.handle;

import org.netty.im.protocol.MessageRequestPacket;
import org.netty.im.protocol.MessageResponsePacket;
import org.netty.im.protocol.Session;
import org.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>消息处理器</p>
 *
 * @author mason
 * @date 2022/3/4 10:55
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(msg.getMessage());

        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            log.warn("[{}] 不在线，发送失败！");
        }

    }
}
