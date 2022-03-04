package org.netty.im.handle;

import java.util.Date;
import java.util.UUID;

import org.netty.im.protocol.LoginRequestPacket;
import org.netty.im.protocol.LoginResponsePacket;
import org.netty.im.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>登录信息返回处理器</p>
 *
 * @author mason
 * @date 2022/3/4 11:43
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            LoginUtil.markLogin(ctx.channel());
            log.info("{}: login success", new Date());
        } else {
            log.info("{}: login failed, the reason is {}", new Date(), msg.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{}: start login", new Date());
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("mason");
        loginRequestPacket.setPassword("123456");
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

}
