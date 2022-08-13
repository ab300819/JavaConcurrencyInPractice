package org.netty.rpc.core.registy.zookeeper;

import java.util.List;

import org.netty.rpc.core.common.event.data.URLChangeWrapper;
import org.netty.rpc.core.registy.AbstractRegister;
import org.netty.rpc.core.registy.RegistryService;
import org.netty.rpc.core.registy.Url;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/7/31 22:25
 */
public class ZookeeperRegister extends AbstractRegister implements RegistryService {

    private AbstractZookeeperClient zookeeperClient;

    private String ROOT = "/mrpc";

    public ZookeeperRegister(String address) {
        this.zookeeperClient = new CuratorZookeeperClient(address);
    }

    @Override
    public void register(Url url) {
        if (!this.zookeeperClient.existNode(ROOT)) {
            zookeeperClient.createPersistentData(ROOT, "");
        }

        String urlStr = Url.buildProviderUrl(url);
        if (!zookeeperClient.existNode(getProviderPath(url))) {
            zookeeperClient.createTemporaryData(getProviderPath(url), urlStr);
        } else {
            zookeeperClient.deleteNode(getProviderPath(url));
            zookeeperClient.createTemporaryData(getProviderPath(url), urlStr);
        }
        super.register(url);
    }

    @Override
    public void unRegister(Url url) {
        zookeeperClient.deleteNode(getProviderPath(url));
        super.unRegister(url);
    }

    @Override
    public void subscribe(Url url) {
        if (!this.zookeeperClient.existNode(ROOT)) {
            zookeeperClient.createPersistentData(ROOT, "");
        }

        String urlStr = Url.buildConsumerUrl(url);
        if (!zookeeperClient.existNode(getConsumerPath(url))) {
            zookeeperClient.createTemporarySeqData(getConsumerPath(url), urlStr);
        } else {
            zookeeperClient.deleteNode(getConsumerPath(url));
            zookeeperClient.createTemporarySeqData(getConsumerPath(url), urlStr);
        }
        super.subscribe(url);
    }

    @Override
    public void doAfterSubscribe(Url url) {
        //监听是否有新的服务注册
        String newServerNodePath = ROOT + "/" + url.getServiceName() + "/provider";
        watchChildNodeData(newServerNodePath);
    }

    public void watchChildNodeData(String newServerNodePath) {
        zookeeperClient.watchChildNodeData(newServerNodePath, watchedEvent -> {
            System.out.println(watchedEvent);
            String path = watchedEvent.getPath();
            List<String> childrenDataList = zookeeperClient.getChildrenData(path);
            URLChangeWrapper urlChangeWrapper = new URLChangeWrapper();
            urlChangeWrapper.setProviderUrl(childrenDataList);
            urlChangeWrapper.setServiceName(path.split("/")[2]);

//            MRpcEvent mRpcEvent = new MRpcUpdateEvent(urlChangeWrapper);
//            MRpcListenerLoader.
        });
    }

    @Override
    public void doBeforeSubscribe(Url url) {

    }

    @Override
    public List<String> getProvider(String serviceName) {
        return zookeeperClient.getChildrenData(ROOT + "/" + serviceName + "/provider");
    }

    private String getProviderPath(Url url) {
        return ROOT + "/" + url.getServiceName() + "/provider/" + url.getParameters().get("host") + ":" + url.getParameters().get("port");
    }

    private String getConsumerPath(Url url) {
        return ROOT + "/" + url.getServiceName() + "/consumer/" + url.getApplicationName() + ":" + url.getParameters().get("host") + ":";
    }

}
