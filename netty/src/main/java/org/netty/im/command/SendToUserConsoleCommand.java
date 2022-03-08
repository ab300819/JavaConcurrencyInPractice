package org.netty.im.command;

import java.util.Scanner;

import org.netty.im.protocol.MessageRequestPacket;
import io.netty.channel.Channel;

public class SendToUserConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner in, Channel channel) {
        String toUserId = in.next();
        String message = in.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
