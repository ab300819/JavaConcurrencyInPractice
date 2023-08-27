package org.netty.rpc.core.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.netty.rpc.core.common.ChannelFuturePollingRef;
import org.netty.rpc.core.common.ChannelFutureWrapper;
import org.netty.rpc.core.common.RpcInvocation;
import org.netty.rpc.core.registy.Url;
import org.netty.rpc.core.router.IRouter;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/22 22:40
 */
public class CommonClientCache {

    public static BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue(100);

    public static final Map<String, Object> RESP_MAP = new HashMap<>();

    public static final List<Url> SUBSCRIBE_SERVICE_LIST = new ArrayList<>();

    public static final Map<String, List<ChannelFutureWrapper>> CONNECT_MAP = new HashMap<>();

    public static final Set<String> SERVER_ADDRESS = new HashSet<>();

    public static final Map<String, ChannelFutureWrapper[]> SERVICE_ROUTER_MAP = new HashMap<>();

    public static ChannelFuturePollingRef CHANNEL_FUTURE_POLLING_REF = new ChannelFuturePollingRef();

    public static IRouter IROUTER;
}
