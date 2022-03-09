package org.netty.im.command;

import java.util.Scanner;

import org.netty.im.protocol.JoinGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();

        log.info("输入 groupId, 加入群聊：");
        String groupId = in.next();

        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
