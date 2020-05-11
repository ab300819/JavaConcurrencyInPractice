package com.netty.demo.protocol.server;

import com.netty.demo.protocol.struct.MessageHeader;
import com.netty.demo.protocol.struct.MessageType;
import com.netty.demo.protocol.struct.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(LoginAuthRespHandler.class);


    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();
    private String[] whiteList = {"127.0.0.1"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手请求消息，处理，其它消息透传
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.LOGIN_REQ.getValue()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp;
            // 重复登陆，拒绝
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = buildResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel()
                        .remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOk = false;
                for (String wip : whiteList) {
                    if (wip.equals(ip)) {
                        isOk = true;
                        break;
                    }
                }
                loginResp = isOk ? buildResponse((byte) 0)
                        : buildResponse((byte) -1);
                if (isOk) {
                    nodeCheck.put(nodeIndex, true);
                }
            }
            log.info("The login response is : {} body [{}]", loginResp, loginResp.getBody());
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 删除缓存
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        MessageHeader header = new MessageHeader();
        header.setType(MessageType.LOGIN_RESP.getValue());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }
}
