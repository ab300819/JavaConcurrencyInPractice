package com.geektime.demo.server.handler;

import com.geektime.demo.common.Operation;
import com.geektime.demo.common.RequestMessage;
import com.geektime.demo.common.auth.AuthOperation;
import com.geektime.demo.common.auth.AuthOperationResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {

        try {

            Operation operation = msg.getMessageBody();
            if (operation instanceof AuthOperation) {

                AuthOperation authOperation = (AuthOperation) operation;
                AuthOperationResult result = authOperation.execute();
                if (result.isPassAuth()) {
                    log.info("pass auth");
                } else {
                    ctx.close();
                    log.error("fail to auth");
                }
            } else {
                log.error("expect first message is auth");
                ctx.close();
            }
        } finally {
            ctx.pipeline().remove(this);
        }

    }
}
