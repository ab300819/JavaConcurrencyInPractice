package org.netty.rpc.core.registy;

/**
 * <p>注册中心</p>
 *
 * @author mason
 * @date 2022/7/8 00:37
 */
public interface RegistryService {

    /**
     * 注册服务
     *
     * @param url 注册中心 url
     */
    void register(Url url);

    /**
     * 服务下线
     *
     * @param url 注册中心 url
     */
    void unRegister(Url url);

    /**
     * 消费者订阅服务
     *
     * @param url 注册中心 url
     */
    void subscribe(Url url);


    /**
     * 执行取消订阅
     *
     * @param url 注册中心 url
     */
    void doUnSubscribe(Url url);

}
