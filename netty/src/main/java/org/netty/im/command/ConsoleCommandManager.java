package org.netty.im.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner in, Channel channel) {
        String command = in.next();

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand != null) {
            consoleCommand.exec(in, channel);
        } else {
            log.info("无法识别【{}】指令，请重新输入！", command);
        }
    }
}
