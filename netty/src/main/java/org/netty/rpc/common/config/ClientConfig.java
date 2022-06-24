package org.netty.rpc.common.config;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/23 22:19
 */
public class ClientConfig {

    private final int port;

    private final String address;

    public ClientConfig(String address, int port) {
        this.port = port;
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }
}
