package org.netty.rpc.common;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import static org.netty.rpc.common.constants.RpcConstants.MAGIC_NUMBER;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/4/18 21:56
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private static final int BASE_LENGTH = 2 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= BASE_LENGTH) {
            if (in.readableBytes() > 1000) {
                in.skipBytes(in.readableBytes());
            }

            int beginReader;
            while (true) {
                beginReader = in.readerIndex();
                in.markReaderIndex();

                if (in.readShort() == MAGIC_NUMBER) {
                    break;
                } else {
                    ctx.close();
                    return;
                }
            }

            int length = in.readInt();
            if (in.readableBytes() < length) {
                in.readerIndex(beginReader);
                return;
            }

            byte[] data = new byte[length];
            in.readBytes(data);
            RpcProtocol protocol = new RpcProtocol(data);
            out.add(protocol);
        }
    }
}
