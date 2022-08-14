package org.netty.rpc.core.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/**
 * @author mengshen
 */
public class PropertiesLoader {

    private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);
    private final String applicationName = "applicationName";
    private final String serverPort = "serverPort";
    private final String registerAddress = "registerAddress";
    private final String proxyType = "proxyType";

    private Map<String, Object> configMap;

    public PropertiesLoader() {
        loadConfig();
    }

    private void loadConfig() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("mrpc.yaml")) {
            configMap = yaml.load(inputStream);
        } catch (IOException e) {
            log.error("file not found");
        }
    }

    public ClientConfig getClientConfig() {
        ClientConfig config = new ClientConfig();
        config.setApplicationName(String.valueOf(configMap.get(applicationName)));
        config.setAddress(String.valueOf(configMap.get(registerAddress)));
        config.setPort(Integer.parseInt(String.valueOf(configMap.get(serverPort))));
        return config;
    }

    public ServerConfig getServerConfig() {
        ServerConfig config = new ServerConfig();
        config.setApplicationName(String.valueOf(configMap.get(applicationName)));
        config.setRegistryPort(Integer.parseInt(String.valueOf(configMap.get(serverPort))));
        config.setRegistryAddress(String.valueOf(configMap.get(registerAddress)));
        return config;
    }

}
