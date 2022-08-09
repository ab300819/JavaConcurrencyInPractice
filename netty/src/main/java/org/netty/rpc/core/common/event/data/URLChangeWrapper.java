package org.netty.rpc.core.common.event.data;

import java.util.List;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/8/3 01:14
 */
public class URLChangeWrapper {

    private String serviceName;
    private List<String> providerUrl;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<String> getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(List<String> providerUrl) {
        this.providerUrl = providerUrl;
    }
}
