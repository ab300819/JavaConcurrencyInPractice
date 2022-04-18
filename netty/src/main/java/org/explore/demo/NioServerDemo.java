package org.explore.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>NIO demo</p>
 *
 * @author mason
 * @date 2022/4/17 23:03
 */
public class NioServerDemo {

    private static final Logger log = LoggerFactory.getLogger(BioServerDemo.class);

    private static final Executor executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        NioServer server = new NioServer();
        server.initServer();
        Selector selector = server.getSelector();
        executor.execute(() -> {
            while (true) {
                try {
                    int selectKey = selector.select();
                    if (selectKey > 0) {
                        Set<SelectionKey> keySet = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = keySet.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove();

                            if (key.isAcceptable()) {
                                server.accept(key);
                            }

                            if (key.isReadable()) {
                                server.read(key);
                            }

                            if (key.isWritable()) {

                            }
                        }
                    }

                } catch (IOException exception) {

                }
            }
        });

    }

    public static class NioServer {

        private Logger log = LoggerFactory.getLogger(NioServer.class);

        private ServerSocketChannel serverSocketChannel = null;
        private Selector selector = null;
        private SelectionKey selectionKey = null;

        private int port;

        public Selector getSelector() {
            return selector;
        }

        public NioServer() {
            this(8181);
        }

        public NioServer(int port) {
            this.port = port;
        }

        public void initServer() {
            try {
                selector = Selector.open();
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(port));
                selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                log.info("occur some error");
            }
        }

        public void accept(SelectionKey key) {
            try {
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = channel.accept();
                log.info("connection is accept");
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("occur some error");
            }
        }

        public void read(SelectionKey key) {
            ByteBuffer byteBuffer = null;
            try {
                SocketChannel channel = (SocketChannel) key.channel();
                byteBuffer = ByteBuffer.allocate(100);
                int len = channel.read(byteBuffer);
                if (len > 0) {
                    byteBuffer.flip();
                    byte[] byteArray = new byte[byteBuffer.limit()];
                    byteBuffer.get(byteArray);
                    log.info("<=== get message {}", new String(byteArray, 0, len));
                    key.interestOps(SelectionKey.OP_READ);
                }
            } catch (IOException e) {
                try {
                    serverSocketChannel.close();
                    selectionKey.cancel();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

}
