package org.netty.im.command;

import java.util.Scanner;

import org.netty.im.protocol.QuitGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();

        log.info("输入 groupId, 退出群聊：");
        String groupId = in.next();

        quitGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
