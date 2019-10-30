package com.netty.demo.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

public class MarshallingDecoder {

    private final Unmarshaller unmarshaller;

    public MarshallingDecoder() throws Exception {
        unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }

    public Object decode(ByteBuf in) throws Exception {

        //1. 读取第一个 4bytes，里面放置的是 object 对象的 byte 长度
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);

        //2. 使用 byteBuf 的代理类
        ByteInput input = new ChannelBufferByteInput(buf);
        try {
            //3. 开始解码
            unmarshaller.start(input);
            Object object = unmarshaller.readObject();
            unmarshaller.finish();
            //4. 读完之后设置读取的位置
            in.readerIndex(in.readerIndex() + objectSize);
            return object;
        } finally {
            unmarshaller.close();
        }
    }
}
