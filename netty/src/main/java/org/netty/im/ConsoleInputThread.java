package org.netty.im;

import java.util.Scanner;

import org.netty.im.protocol.MessageRequestPacket;
import org.netty.im.util.LoginUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleInputThread implements Runnable {

    private final Channel channel;

    public ConsoleInputThread(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (LoginUtil.hasLogin(channel)) {
                log.info("输入消息发送至服务端: ");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();

                MessageRequestPacket packet = new MessageRequestPacket();
                packet.setMessage(line);
                channel.writeAndFlush(packet);
            }
        }
    }
}
