package com.geektime.demo.client.codec;

import com.common.util.IdUtil;
import com.geektime.demo.common.Operation;
import com.geektime.demo.common.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Operation msg, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), msg);
        out.add(requestMessage);
    }

}
