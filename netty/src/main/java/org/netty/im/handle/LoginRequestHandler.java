package org.netty.im.handle;

import java.util.UUID;

import org.netty.im.protocol.LoginRequestPacket;
import org.netty.im.protocol.LoginResponsePacket;
import org.netty.im.protocol.Session;
import org.netty.im.util.SessionUtil;
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
        LoginResponsePacket loginResponse = new LoginResponsePacket();
        loginResponse.setVersion(msg.getVersion());
        loginResponse.setUserName(msg.getUserName());

        if (valid(msg)) {
            loginResponse.setSuccess(true);
            String userId = randomUserId();
            loginResponse.setUserId(userId);
            SessionUtil.bindSession(new Session(userId, msg.getUserName()), ctx.channel());
            log.info("[{}]登陆成功！", loginResponse.getUserName());
        } else {
            loginResponse.setReason("fail to login");
            loginResponse.setSuccess(false);
            log.info("login failed");
        }

        ctx.channel().writeAndFlush(loginResponse);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket packet) {
        return true;
    }

    private String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
