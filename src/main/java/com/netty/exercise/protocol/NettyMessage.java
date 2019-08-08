package com.netty.exercise.protocol;

import lombok.Data;

@Data
public class NettyMessage {

    private MessageHeader header;
    private Object body;

}
