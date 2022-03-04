package org.netty.im.handle;

import org.netty.im.protocol.LoginRequestPacket;
import org.netty.im.protocol.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginRequestHandler  extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        if (valid(msg)) {
            responsePacket.setSuccess(true);
            log.info("login success");
        } else {
            responsePacket.setReason("fail to login");
            responsePacket.setSuccess(false);
            log.info("login failed");
        }
        ByteBuf responseByteBuf = packetCodec.encode(ctx.alloc(), responsePacket);
        ctx.writeAndFlush(responseByteBuf);
    }

    private boolean valid(LoginRequestPacket packet) {
        return true;
    }
}
