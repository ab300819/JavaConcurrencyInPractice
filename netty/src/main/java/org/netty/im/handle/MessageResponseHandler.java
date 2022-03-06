package org.netty.im.handle;

import org.netty.im.protocol.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>信息返回处理器</p>
 *
 * @author mason
 * @date 2022/3/4 11:46
 */
@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        String userId = msg.getFromUserId();
        String userName = msg.getFromUserName();
        log.info("{}:{} -> {}", userId, userName, msg.getMessage());
    }
}
