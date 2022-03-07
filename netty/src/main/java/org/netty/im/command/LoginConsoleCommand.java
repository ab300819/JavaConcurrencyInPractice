package org.netty.im.command;

import java.util.Scanner;

import org.netty.im.protocol.LoginRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner in, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        log.info("输入用户名登陆: ");
        String userName = in.nextLine();
        loginRequestPacket.setUserName(userName);
        loginRequestPacket.setPassword("pwd");
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
