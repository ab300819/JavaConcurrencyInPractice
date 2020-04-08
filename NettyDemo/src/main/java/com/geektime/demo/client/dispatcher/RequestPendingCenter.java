package com.geektime.demo.client.dispatcher;

import com.geektime.demo.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {

    private Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(Long streamId, OperationResultFuture future) {
        map.put(streamId, future);
    }

    public void set(Long streamId, OperationResult future) {
        OperationResultFuture operationResultFuture = map.get(streamId);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(future);
            map.remove(streamId);
        }
    }

}
