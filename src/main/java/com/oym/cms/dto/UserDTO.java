package com.oym.cms.dto;

import com.oym.cms.entity.User;

import java.util.List;

/**
 * 用户数据传输对象
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:23
 */
public class UserDTO {
    private User user;
    private Integer msg;
    private List<User> userList;

    public UserDTO() {}

    public UserDTO(User user, Integer msg) {
        this.user = user;
        this.msg = msg;
    }

    public UserDTO(Integer msg, List<User> userList) {
        this.msg = msg;
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getMsg() {
        return msg;
    }

    public void setMsg(Integer msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "user=" + user +
                ", msg='" + msg + '\'' +
                '}';
    }
}
