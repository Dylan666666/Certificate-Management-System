package com.oym.cms.dto;

import com.oym.cms.entity.User;

/**
 * 用户数据传输对象
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:23
 */
public class UserDTO {
    private User user;
    private Integer msg;

    public UserDTO() {}

    public UserDTO(User user, Integer msg) {
        this.user = user;
        this.msg = msg;
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
