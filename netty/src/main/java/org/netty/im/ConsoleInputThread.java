package org.netty.im;

import java.util.Scanner;

import org.netty.im.command.ConsoleCommandManager;
import org.netty.im.command.LoginConsoleCommand;
import org.netty.im.util.SessionUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleInputThread implements Runnable {

    private final Channel channel;
    private final Scanner sc;
    private final ConsoleCommandManager consoleCommandManager;
    private final LoginConsoleCommand loginConsoleCommand;

    public ConsoleInputThread(Channel channel) {
        consoleCommandManager = new ConsoleCommandManager();
        loginConsoleCommand = new LoginConsoleCommand();
        this.channel = channel;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (!SessionUtil.hasLogin(channel)) {
                loginConsoleCommand.exec(sc, channel);
            } else {
                log.info("输入信息：");
                consoleCommandManager.exec(sc, channel);
            }
        }
    }
}
