package org.netty.rpc.core.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.netty.rpc.core.common.RpcDecoder;
import org.netty.rpc.core.common.RpcEncoder;
import org.netty.rpc.core.common.cache.CommonServerCache;
import org.netty.rpc.core.common.config.ServerConfig;
import org.netty.rpc.core.registy.RegistryService;
import org.netty.rpc.core.registy.Url;
import org.netty.rpc.core.registy.zookeeper.ZookeeperRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import static org.netty.rpc.core.common.cache.CommonServerCache.PROVIDER_URL_SET;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/4/18 21:47
 */
public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private static final ExecutorService executorService = Executors.newFixedThreadPool(8);

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ServerConfig serverConfig;

    private RegistryService registryService;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void startApplication() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.info("init provider");
                        ch.pipeline().addLast(new RpcEncoder());
                        ch.pipeline().addLast(new RpcDecoder());
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });
        batchExportUrl();
        bootstrap.bind(serverConfig.getRegistryPort()).sync();
    }

    public void initServerConfig() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setRegistryPort(9090);
        serverConfig.setApplicationName("irpc-provider");
        serverConfig.setRegistryAddress("localhost:2181");
        setServerConfig(serverConfig);
    }

    public void registryServer(Object serviceBean) {
        if (serviceBean.getClass().getInterfaces().length == 0) {
            throw new RuntimeException("service must had interfaces!");
        }

        Class<?>[] clazzArray = serviceBean.getClass().getInterfaces();
        if (clazzArray.length > 1) {
            throw new RuntimeException("service must only had one interface!");
        }

        if (registryService == null) {
            registryService = new ZookeeperRegister(serverConfig.getRegistryAddress());
        }

        Class<?> interfaceClass = clazzArray[0];
        CommonServerCache.PROVIDER_CLASS_MAP.put(interfaceClass.getName(), serviceBean);
        Url url = new Url();
        url.setApplicationName(serverConfig.getApplicationName());
        url.setServiceName(interfaceClass.getName());
        url.addParameter("host", "127.0.0.1");
        url.addParameter("port", String.valueOf(serverConfig.getRegistryPort()));
        PROVIDER_URL_SET.add(url);
    }

    public void batchExportUrl() {
        for (Url url : PROVIDER_URL_SET) {
            executorService.submit(() -> registryService.register(url));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        server.initServerConfig();
        server.registryServer(new DataServiceImpl());
        server.startApplication();
    }
}
