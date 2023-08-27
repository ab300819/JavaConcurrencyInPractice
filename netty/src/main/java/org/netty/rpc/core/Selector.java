package org.netty.rpc.core;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/9/4 22:46
 */
public class Selector {

    /**
     * 筛选策略
     */
    private String selectStrategy;

    /**
     * 服务命名
     * eg: com.sise.test.DataService
     */
    private String providerServiceName;

    public String getSelectStrategy() {
        return selectStrategy;
    }

    public void setSelectStrategy(String selectStrategy) {
        this.selectStrategy = selectStrategy;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public void setProviderServiceName(String providerServiceName) {
        this.providerServiceName = providerServiceName;
    }
}
