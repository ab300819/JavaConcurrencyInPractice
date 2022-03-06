package org.netty.im;

import java.util.Scanner;

import org.netty.im.protocol.LoginRequestPacket;
import org.netty.im.protocol.MessageRequestPacket;
import org.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleInputThread implements Runnable {

    private final Channel channel;
    private final Scanner sc;

    public ConsoleInputThread(Channel channel) {
        this.channel = channel;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (!SessionUtil.hasLogin(channel)) {
                LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                log.info("输入用户名登陆: ");
                String userName = sc.nextLine();
                loginRequestPacket.setUserName(userName);
                loginRequestPacket.setPassword("pwd");
                channel.writeAndFlush(loginRequestPacket);
                waitForLoginResponse();
            } else {
                log.info("输入信息：");
                String toUserId = sc.next();
                String message = sc.next();
                channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
            }
        }
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
