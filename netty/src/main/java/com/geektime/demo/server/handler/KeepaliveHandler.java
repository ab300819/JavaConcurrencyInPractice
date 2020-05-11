package com.geektime.demo.server.handler;

import com.common.util.IdUtil;
import com.geektime.demo.common.RequestMessage;
import com.geektime.demo.common.keepalive.KeepaliveOperation;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeepaliveHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT) {
            log.info("write idle happen. so need to send keepalive to keep connection not close by sender.");
            KeepaliveOperation keepaliveOperation = new KeepaliveOperation();
            RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), keepaliveOperation);
            ctx.writeAndFlush(requestMessage);
        }

        super.userEventTriggered(ctx, evt);
    }
}
