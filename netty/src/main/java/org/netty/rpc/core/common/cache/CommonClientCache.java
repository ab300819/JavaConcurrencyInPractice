package org.netty.rpc.core.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.netty.rpc.core.common.RpcInvocation;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/22 22:40
 */
public class CommonClientCache {

    public static BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue(100);

    public static final Map<String, Object> RESP_MAP = new HashMap<>();

    public static final List<String> SUBSCRIBE_SERVICE_LIST = new ArrayList<>();
}
