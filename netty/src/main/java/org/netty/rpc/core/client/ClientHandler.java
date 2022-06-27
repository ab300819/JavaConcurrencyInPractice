package org.netty.rpc.core.client;

import org.netty.rpc.core.common.RpcInvocation;
import org.netty.rpc.core.common.RpcProtocol;
import org.netty.rpc.core.common.cache.CommonClientCache;
import com.common.util.JsonUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/22 23:26
 */
public class ClientHandler extends SimpleChannelInboundHandler<RpcProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol msg) throws Exception {
        byte[] responseContent = msg.getContent();
        String json = new String(responseContent, 0, responseContent.length);
        RpcInvocation rpcInvocation = JsonUtil.toObject(json, RpcInvocation.class);
        if (!CommonClientCache.RESP_MAP.containsKey(rpcInvocation.getUuid())) {
            throw new IllegalArgumentException("server response is error!");
        }
        CommonClientCache.RESP_MAP.put(rpcInvocation.getUuid(), rpcInvocation);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }
}
