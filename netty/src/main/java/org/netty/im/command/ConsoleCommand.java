package org.netty.im.command;

import java.util.Scanner;

import io.netty.channel.Channel;

/**
 * <p>命令接口</p>
 */
public interface ConsoleCommand {

    void exec(Scanner in, Channel channel);

}
