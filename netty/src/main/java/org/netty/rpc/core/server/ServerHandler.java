package org.netty.rpc.core.server;

import java.lang.reflect.Method;

import org.netty.rpc.core.common.RpcInvocation;
import org.netty.rpc.core.common.RpcProtocol;
import com.common.util.JsonUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static org.netty.rpc.core.common.cache.CommonServerCache.PROVIDER_CLASS_MAP;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/4/18 22:14
 */
public class ServerHandler extends SimpleChannelInboundHandler<RpcProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol msg) throws Exception {

        String json = new String(msg.getContent(), 0, msg.getContentLength());
        RpcInvocation rpcInvocation = JsonUtil.toObject(json, RpcInvocation.class);
        Object targetBean = PROVIDER_CLASS_MAP.get(rpcInvocation.getTargetMethod());
        Method[] methodArray = targetBean.getClass().getDeclaredMethods();
        Object result = null;
        for (Method method : methodArray) {
            if (method.getName().equals(rpcInvocation.getTargetMethod())) {
                if (method.getReturnType().equals(Void.TYPE)) {
                    method.invoke(targetBean, rpcInvocation.getArgs());
                } else {
                    result = method.invoke(targetBean, rpcInvocation.getArgs());
                }
                break;
            }
        }
        rpcInvocation.setResponse(result);
        RpcProtocol responseProtocol = new RpcProtocol(JsonUtil.toString(rpcInvocation).getBytes());
        ctx.channel().writeAndFlush(responseProtocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }
}
