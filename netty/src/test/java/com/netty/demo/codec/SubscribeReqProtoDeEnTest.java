package com.netty.demo.codec;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscribeReqProtoDeEnTest {

    @Test
    public void testSubscribe() throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req= SubscribeReqProtoDeEn.createSubscribeReq();
        SubscribeReqProto.SubscribeReq req2= SubscribeReqProtoDeEn.decode(SubscribeReqProtoDeEn.encode(req));
        assertEquals(req2,req);

    }

}
