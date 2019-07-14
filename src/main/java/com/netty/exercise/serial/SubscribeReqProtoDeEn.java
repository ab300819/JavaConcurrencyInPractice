package com.netty.exercise.serial;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

public class SubscribeReqProtoDeEn {

    public static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("Mason");
        builder.setProductName("netty book");

        List<String> address = new ArrayList<>();
        address.add("Nan Jing");
        address.add("Bei Jing");
        address.add("Shen Zheng");
        address.add("Hong Kong");
        builder.addAllAddress(address);
        return builder.build();
    }

    public static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    public static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

}
