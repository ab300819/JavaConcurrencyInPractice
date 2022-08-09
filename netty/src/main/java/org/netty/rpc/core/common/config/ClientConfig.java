package org.netty.rpc.core.common.config;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/23 22:19
 */
public class ClientConfig {

    private final String applicationName;

    private final int port;

    private final String address;

    private String proxyType;

    public ClientConfig(String applicationName, int port, String address, String proxyType) {
        this.applicationName = applicationName;
        this.port = port;
        this.address = address;
        this.proxyType = proxyType;
    }

    public ClientConfig(String applicationName, int port, String address) {
        this(applicationName, port, address, null);
    }

    public String getApplicationName() {
        return applicationName;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public String getProxyType() {
        return proxyType;
    }
}
