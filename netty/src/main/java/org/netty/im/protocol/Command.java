package org.netty.im.protocol;

/**
 * <p>操作枚举</p>
 *
 * @author mason
 */
public interface Command {

    /**
     * 登录请求
     */
    byte LOGIN_REQUEST = 1;

    /**
     * 登录返回
     */
    byte LOGIN_RESPONSE = 2;


    byte message_request=3;

}
