package org.netty.rpc.core.registy.zookeeper;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.Watcher;

/**
 * <p></p>
 *
 * @author mengshen
 * @date 2022/7/18 17:52
 */
public abstract class AbstractZookeeperClient {

    private String zkAddress;
    private int baseSleepTimes;
    private int maxRetryTimes;

    protected AbstractZookeeperClient(String zkAddress) {
        this(zkAddress, 1000, 3);
    }

    protected AbstractZookeeperClient(String zkAddress, int baseSleepTimes, int maxRetryTimes) {
        this.zkAddress = zkAddress;
        this.baseSleepTimes = baseSleepTimes;
        this.maxRetryTimes = maxRetryTimes;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public int getBaseSleepTimes() {
        return baseSleepTimes;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public abstract void updateNodeData(String address, String data);

    public abstract CuratorFramework getClient();

    /**
     * 拉取节点的数据
     *
     * @param address
     * @return
     */
    public abstract String getNodeData(String address);

    /**
     * 获取指定目录下的字节点数据
     *
     * @param path
     * @return
     */
    public abstract List<String> getChildrenData(String path);

    /**
     * 创建持久化类型节点数据信息
     *
     * @param address
     * @param data
     */
    public abstract void createPersistentData(String address, String data);

    /**
     * @param address
     * @param data
     */
    public abstract void createPersistentWithSeqData(String address, String data);


    /**
     * 创建有序且临时类型节点数据信息
     *
     * @param address
     * @param data
     */
    public abstract void createTemporarySeqData(String address, String data);


    /**
     * 创建临时节点数据类型信息
     *
     * @param address
     * @param data
     */
    public abstract void createTemporaryData(String address, String data);

    /**
     * 设置某个节点的数值
     *
     * @param address
     * @param data
     */
    public abstract void setTemporaryData(String address, String data);

    /**
     * 断开zk的客户端链接
     */
    public abstract void destroy();


    /**
     * 展示节点下边的数据
     *
     * @param address
     */
    public abstract List<String> listNode(String address);


    /**
     * 删除节点下边的数据
     *
     * @param address
     * @return
     */
    public abstract boolean deleteNode(String address);


    /**
     * 判断是否存在其他节点
     *
     * @param address
     * @return
     */
    public abstract boolean existNode(String address);


    /**
     * 监听path路径下某个节点的数据变化
     *
     * @param path
     */
    public abstract void watchNodeData(String path, Watcher watcher);

    /**
     * 监听子节点下的数据变化
     *
     * @param path
     * @param watcher
     */
    public abstract void watchChildNodeData(String path, Watcher watcher);
}
