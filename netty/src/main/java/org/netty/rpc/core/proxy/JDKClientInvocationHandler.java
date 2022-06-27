package org.netty.rpc.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.netty.rpc.core.common.RpcInvocation;
import org.netty.rpc.core.common.cache.CommonClientCache;

import static org.netty.rpc.core.common.cache.CommonClientCache.RESP_MAP;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/26 12:14
 */
public class JDKClientInvocationHandler implements InvocationHandler {

    private static final Object OBJECT = new Object();

    private Class<?> clazz;

    public JDKClientInvocationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setArgs(args);
        rpcInvocation.setTargetMethod(method.getName());
        rpcInvocation.setTargetServiceName(clazz.getName());
        rpcInvocation.setUuid(UUID.randomUUID().toString());

        RESP_MAP.put(rpcInvocation.getUuid(), OBJECT);
        CommonClientCache.SEND_QUEUE.add(rpcInvocation);

        long beginTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - beginTime < 3 * 1000) {
            Object object=RESP_MAP.get(rpcInvocation.getUuid());
            if(object instanceof  RpcInvocation){
                return ((RpcInvocation)object).getResponse();
            }
        }
        throw new TimeoutException("client wait server's response timeout!");
    }
}
