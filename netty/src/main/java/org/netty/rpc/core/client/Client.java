package org.netty.rpc.core.client;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.netty.rpc.core.common.RpcDecoder;
import org.netty.rpc.core.common.RpcEncoder;
import org.netty.rpc.core.common.RpcInvocation;
import org.netty.rpc.core.common.RpcProtocol;
import org.netty.rpc.core.common.cache.CommonClientCache;
import org.netty.rpc.core.common.config.ClientConfig;
import org.netty.rpc.core.common.config.PropertiesLoader;
import org.netty.rpc.core.common.event.MRpcListenerLoader;
import org.netty.rpc.core.common.utils.CommonUtils;
import org.netty.rpc.core.proxy.JDKProxyFactory;
import org.netty.rpc.core.proxy.JavassistProxyFactory;
import org.netty.rpc.core.registy.AbstractRegister;
import org.netty.rpc.core.registy.Url;
import org.netty.rpc.core.registy.zookeeper.ZookeeperRegister;
import org.netty.rpc.interfaces.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.common.util.JsonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static org.netty.rpc.core.common.cache.CommonClientCache.SUBSCRIBE_SERVICE_LIST;

/**
 * <p></p>
 *
 * @author mason
 * @date 2022/6/22 23:27
 */
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    protected static final Executor executor = Executors.newFixedThreadPool(8);

    private ClientConfig clientConfig;

    private AbstractRegister register;

    private MRpcListenerLoader mRpcListenerLoader;

    private Bootstrap bootstrap;

    public ClientConfig getClientConfig() {
        return clientConfig;
    }


    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public MRpcListenerLoader getmRpcListenerLoader() {
        return mRpcListenerLoader;
    }

    public void setmRpcListenerLoader(MRpcListenerLoader mRpcListenerLoader) {
        this.mRpcListenerLoader = mRpcListenerLoader;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public Client() {
        this.bootstrap = new Bootstrap();
    }

    public RpcReference initClient() {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        bootstrap.group(clientGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        mRpcListenerLoader = new MRpcListenerLoader();
        mRpcListenerLoader.init();

        PropertiesLoader loader = new PropertiesLoader();
        clientConfig = loader.getClientConfig();
        RpcReference rpcReference;
        if ("javassist".equals(clientConfig.getProxyType())) {
            rpcReference = new RpcReference(new JavassistProxyFactory());
        } else {
            rpcReference = new RpcReference(new JDKProxyFactory());
        }
        return rpcReference;
    }

    public void doSubscribeService(Class<?> serviceBean) {
        if (register == null) {
            register = new ZookeeperRegister(clientConfig.getAddress());
        }
        Url url = new Url();
        url.setApplicationName(clientConfig.getApplicationName());
        url.setServiceName(serviceBean.getName());
        url.addParameter(Url.HOST, CommonUtils.getLocalIP());
        register.subscribe(url);

    }

    public void doConnectServer() {
        for (String providerServiceName : SUBSCRIBE_SERVICE_LIST) {
            List<String> providerIps = register.getProvider(providerServiceName);
            for (String providerIp : providerIps) {
                try {
                    ConnectionHandler.connect(providerServiceName, providerIp);
                } catch (InterruptedException e) {
                    log.error("doConnectServer connect fail", e);
                }
            }
            Url url = new Url();
            url.setServiceName(providerServiceName);
            register.doAfterSubscribe(url);
        }
    }

    public void startClient() {
        executor.execute(new AsyncSendJob());
    }

    public static class AsyncSendJob implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    //阻塞模式
                    RpcInvocation data = CommonClientCache.SEND_QUEUE.take();
                    String json = JsonUtil.toString(data);
                    RpcProtocol rpcProtocol = new RpcProtocol(json.getBytes());
                    ChannelFuture channelFuture = ConnectionHandler.getChannelFuture(data.getTargetServiceName());
                    channelFuture.channel().writeAndFlush(rpcProtocol);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        RpcReference rpcReference = client.initClient();
        DataService dataService = rpcReference.get(DataService.class);
        client.doSubscribeService(DataService.class);
        ConnectionHandler.setBootstrap(client.getBootstrap());
        client.doConnectServer();
        client.startClient();
        for (int i = 0; i < 100; i++) {
            try {
                String result = dataService.sendData("test");
                log.info(result);
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
