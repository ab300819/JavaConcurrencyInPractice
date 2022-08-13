package org.netty.rpc.core.common.config;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/4/18 21:54
 */
public class ServerConfig {

    private Integer registryPort;
    private String registryAddress;
    private String applicationName;
    private String serviceName;

    public Integer getRegistryPort() {
        return registryPort;
    }

    public void setRegistryPort(Integer registryPort) {
        this.registryPort = registryPort;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
