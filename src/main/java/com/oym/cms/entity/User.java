package com.oym.cms.entity;

import java.io.Serializable;

/**
 * 用户类
 * @Author: Mr_OO
 * @Date: 2022/3/2 14:43
 */
public class User implements Serializable {
    private static final long serialVersionUID = 238357573261740184L;
    
    /**
     * 用户id(学号)
     */
    private String userId;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 用户职别
     */
    private Integer userPosition;
    /**
     * 用户账号状态
     */
    private Integer userStatus;
    /**
     * token
     */
    private String userToken;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(Integer userPosition) {
        this.userPosition = userPosition;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", userPosition=" + userPosition +
                ", userStatus=" + userStatus +
                '}';
    }
}
