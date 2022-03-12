package org.netty.im.command;

import java.util.Scanner;

import org.netty.im.protocol.SendToGroupRequestPacket;
import io.netty.channel.Channel;

public class SendToGroupCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        String toGroupId = in.next();
        String message = in.next();
        SendToGroupRequestPacket sendToGroupRequestPacket = new SendToGroupRequestPacket();
        sendToGroupRequestPacket.setToGroupId(toGroupId);
        sendToGroupRequestPacket.setMsg(message);
        channel.writeAndFlush(sendToGroupRequestPacket);
    }
}
