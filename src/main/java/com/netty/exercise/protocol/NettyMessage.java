package com.netty.exercise.protocol;

public class NettyMessage {

    private MessageHeader header;
    private Object body;

    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
