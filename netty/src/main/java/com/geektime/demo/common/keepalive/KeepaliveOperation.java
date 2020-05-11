package com.geektime.demo.common.keepalive;


import com.geektime.demo.common.Operation;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class KeepaliveOperation extends Operation {

    private long time;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    @Override
    public KeepaliveOperationResult execute() {
        KeepaliveOperationResult orderResponse = new KeepaliveOperationResult(time);
        return orderResponse;
    }
}
