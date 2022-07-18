package org.netty.rpc.core.registy;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/7/18 00:25
 */
public class ProviderNodeInfo {

    private String serviceName;

    private String address;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
