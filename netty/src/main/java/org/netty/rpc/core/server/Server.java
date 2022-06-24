package org.netty.rpc.core.server;

import org.netty.rpc.core.common.RpcDecoder;
import org.netty.rpc.core.common.RpcEncoder;
import org.netty.rpc.core.common.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/4/18 21:47
 */
public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ServerConfig serverConfig;

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public void setBossGroup(EventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

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
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<ServerChannel>() {
                    @Override
                    protected void initChannel(ServerChannel ch) throws Exception {
                        log.info("init provider");
                        ch.pipeline().addLast(new RpcEncoder());
                        ch.pipeline().addLast(new RpcDecoder());
                        ch.pipeline().addLast(new ServerHandler());
                    }
                })
                .bind(serverConfig.getPort())
                .sync();
    }

    public void registyServer() {

    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        ServerConfig config = new ServerConfig();
        config.setPort(9090);
        server.setServerConfig(config);
        server.registyServer();
        server.startApplication();

    }
}
