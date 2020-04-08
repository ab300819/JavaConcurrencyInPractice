package com.geektime.demo.common.keepalive;

import io.netty.example.study.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
