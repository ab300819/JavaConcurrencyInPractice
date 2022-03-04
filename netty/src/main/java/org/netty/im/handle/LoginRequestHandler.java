package org.netty.im.handle;

import org.netty.im.protocol.LoginRequestPacket;
import org.netty.im.protocol.LoginResponsePacket;
import org.netty.im.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>登录请求处理器</p>
 *
 * @author mason
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        if (valid(msg)) {
            responsePacket.setSuccess(true);
            LoginUtil.markLogin(ctx.channel());
            log.info("login success");
        } else {
            responsePacket.setReason("fail to login");
            responsePacket.setSuccess(false);
            log.info("login failed");
        }

        ctx.channel().writeAndFlush(responsePacket);
    }

    private boolean valid(LoginRequestPacket packet) {
        return true;
    }
}
