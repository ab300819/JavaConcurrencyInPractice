package org.netty.rpc.core.common.cache;

import java.util.HashMap;
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
public class CommonServerCache {

    public static BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue(100);

    public static final Map<String, Object> PROVIDER_CLASS_MAP = new HashMap<>();

}
