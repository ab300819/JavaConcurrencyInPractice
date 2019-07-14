package com.netty.exercise.serial;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubscribeReqProtoDeEnTest {

    @Test
    public void testSubscribe() throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req= SubscribeReqProtoDeEn.createSubscribeReq();
        SubscribeReqProto.SubscribeReq req2= SubscribeReqProtoDeEn.decode(SubscribeReqProtoDeEn.encode(req));
        assertEquals(req,req2);

    }

}