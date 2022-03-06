package org.netty.im.handle;

import java.util.Date;

import org.netty.im.protocol.LoginResponsePacket;
import org.netty.im.protocol.Session;
import org.netty.im.util.SessionUtil;
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
        String userId = msg.getUserId();
        String userName = msg.getUserName();

        if (msg.isSuccess()) {
            log.info("[{}]登陆成功，userId 为：{}", msg.getUserName(), msg.getUserId());
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
            log.info("{}: login success", new Date());
        } else {
            log.info("{}: {} login failed, the reason is {}", new Date(), msg.getUserName(), msg.getReason());
        }
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("{}: start login", new Date());
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUserName("mason");
//        loginRequestPacket.setPassword("123456");
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接被关闭！");
    }
}
