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

    /**
     * 消息请求
     */
    byte MESSAGE_REQUEST = 3;

    /**
     * 消息返回
     */
    byte MESSAGE_RESPONSE = 4;

    /**
     * 退出请求
     */
    byte LOGOUT_REQUEST = 5;

    /**
     * 退出请求返回
     */
    byte LOGOUT_RESPONSE = 6;

    /**
     * 创建群请求
     */
    byte CREATE_GROUP_REQUEST = 7;

    /**
     * 创建群返回
     */
    byte CREATE_GROUP_RESPONSE = 8;

    /**
     * 展示群成员请求
     */
    byte LIST_GROUP_MEMBERS_REQUEST=9;

    /**
     * 展示群成员返回
     */
    byte LIST_GROUP_MEMBERS_RESPONSE=10;

    /**
     * 加入群组请求
     */
    byte JOIN_GROUP_REQUEST = 11;

    /**
     * 加入群组返回
     */
    byte JOIN_GROUP_RESPONSE = 12;

    /**
     * 退出群组请求
     */
    byte QUIT_GROUP_REQUEST = 13;

    /**
     * 退出群组返回
     */
    byte QUIT_GROUP_RESPONSE = 14;

}
