package org.netty.im.command;

import java.util.Scanner;

import org.netty.im.protocol.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>列出群聊成员</p>
 *
 * @author Mason
 * @date 2022/3/11 11:45
 */
@Slf4j
public class ListGroupMembersCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner in, Channel channel) {
        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();

        log.info("输入 groupId, 列出成员：");
        String groupId = in.next();

        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
