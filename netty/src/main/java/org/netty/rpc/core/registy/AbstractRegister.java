package org.netty.rpc.core.registy;

import java.util.List;

import static org.netty.rpc.core.common.cache.CommonClientCache.SUBSCRIBE_SERVICE_LIST;
import static org.netty.rpc.core.common.cache.CommonServerCache.PROVIDER_URL_SET;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/7/18 00:34
 */
public abstract class AbstractRegister implements RegistryService {

    @Override
    public void register(Url url) {
        PROVIDER_URL_SET.add(url);
    }

    @Override
    public void unRegister(Url url) {
        PROVIDER_URL_SET.remove(url);
    }

    @Override
    public void subscribe(Url url) {
        SUBSCRIBE_SERVICE_LIST.add(url.getServiceName());
    }

    @Override
    public void doUnSubscribe(Url url) {
        SUBSCRIBE_SERVICE_LIST.remove(url.getServiceName());
    }

    public abstract void doAfterSubscribe(Url url);

    public abstract void doBeforeSubscribe(Url url);

    public abstract List<String> getProvider(String serviceName);

}
