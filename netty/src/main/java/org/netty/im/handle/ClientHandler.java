package org.netty.im.handle;

import java.nio.channels.SocketChannel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<SocketChannel> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SocketChannel msg) throws Exception {

    }
}
