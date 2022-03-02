package org.netty.im.util;

import org.netty.im.protocol.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * <p>登录操作</p>
 *
 * @author mengshen
 * @date 2022/1/10 17:42
 */
public class LoginUtil {

    public static void markLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }

}
