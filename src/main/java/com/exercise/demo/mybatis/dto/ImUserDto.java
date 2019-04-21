package com.exercise.demo.mybatis.dto;

/**
 * 用户信息表
 * im_user
 *
 * @date 2019-04-21 23:56:54
 * @author mason
 */
public class ImUserDto {
    /**
     * 主键id
     * im_user.id
     */
    private Integer id;

    /**
     * 用户 uuid
     * im_user.user_id
     */
    private String userId;

    /**
     * 用户名
     * im_user.user_name
     */
    private String userName;

    /**
     * 用户密码
     * im_user.user_password
     */
    private String userPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}