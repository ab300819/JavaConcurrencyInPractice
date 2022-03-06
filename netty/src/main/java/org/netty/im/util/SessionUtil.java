package org.netty.im.util;

import java.util.concurrent.ConcurrentHashMap;

import org.netty.im.protocol.Attributes;
import org.netty.im.protocol.Session;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * <p>登录操作</p>
 *
 * @author mengshen
 * @date 2022/1/10 17:42
 */
public class SessionUtil {

    private final static ConcurrentHashMap<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Session> loginAttr = channel.attr(Attributes.SESSION);
        return loginAttr.get() != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

}
