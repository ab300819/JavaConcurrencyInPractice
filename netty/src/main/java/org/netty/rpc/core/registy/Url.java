package org.netty.rpc.core.registy;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>注册 url</p>
 *
 * @author mason
 * @date 2022/7/8 00:43
 */
public class Url {

    public static final String HOST = "host";
    public static final String PORT = "port";

    private String applicationName;

    private String serviceName;

    private Map<String, String> parameters;

    public Url() {
        this.parameters = new HashMap<>();
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
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

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static String buildProviderUrl(Url url) {
        String host = url.getParameters().get(HOST);
        String port = url.getParameters().get(PORT);
        return new String((url.getApplicationName() + ";"
                + url.getServiceName() + ";"
                + host + ":" + port + ";"
                + System.currentTimeMillis()).getBytes(),
                StandardCharsets.UTF_8);
    }

    public static String buildConsumerUrl(Url url) {
        String host = url.getParameters().get(HOST);
        return new String((url.getApplicationName() + ";"
                + url.getServiceName() + ";"
                + host + ";"
                + System.currentTimeMillis()).getBytes(),
                StandardCharsets.UTF_8);
    }

    public static ProviderNodeInfo buildNodeInfoFromUrl(String providerNode) {
        String[] items = providerNode.split("/");
        ProviderNodeInfo providerNodeInfo = new ProviderNodeInfo();
        providerNodeInfo.setServiceName(items[2]);
        providerNodeInfo.setAddress(items[4]);
        return providerNodeInfo;
    }
}
