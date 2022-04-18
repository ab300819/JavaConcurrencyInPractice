package org.explore.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>BIO demo</p>
 *
 * @author mason
 * @date 2022/4/17 17:38
 */
public class BioServerDemo {

    private static final Logger log = LoggerFactory.getLogger(BioServerDemo.class);
    private static final Executor executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8181));


        while (true) {
            Socket socket = serverSocket.accept();
            log.info("获取新连接");
            executor.execute(() -> {
                while (true) {
                    InputStream inputStream;
                    try {
                        inputStream = socket.getInputStream();
                        byte[] result = new byte[1024];
                        int read = inputStream.read(result);
                        if (read != -1) {
                            log.info("<=== get message {}", new String(result, 0, read));
                            OutputStream outputStream = socket.getOutputStream();
                            outputStream.write("response message".getBytes(StandardCharsets.UTF_8));
                            outputStream.flush();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            });
        }


    }

}
