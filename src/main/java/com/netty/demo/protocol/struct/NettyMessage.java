package com.netty.demo.protocol.struct;

import lombok.Data;

@Data
public class NettyMessage {

    private MessageHeader header;
    private Object body;

}
