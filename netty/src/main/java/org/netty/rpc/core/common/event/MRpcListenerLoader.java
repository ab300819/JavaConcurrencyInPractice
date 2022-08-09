package org.netty.rpc.core.common.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/3 01:21
 */
public class MRpcListenerLoader {

    private static final List<MRpcListener> rpcListenerList = new ArrayList<>();

    private static final ExecutorService eventThreadPool = Executors.newFixedThreadPool(4);

    public static void sendEvent(MRpcEvent rpcEvent) {
        if (rpcListenerList.isEmpty()) {
            return;
        }
        for (MRpcListener<?> rpcListener : rpcListenerList) {
            Class<?> type = getInterfaceT(rpcListener);

            if (!rpcEvent.getClass().equals(type)) {
                continue;
            }
            eventThreadPool.submit(() -> rpcListener.callback(rpcEvent.getData()));
        }

    }


    public static Class<?> getInterfaceT(Object o) {
        Type[] types = o.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[0];
        Type type = parameterizedType.getActualTypeArguments()[0];
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        return null;
    }

    public void init() {

    }
}
