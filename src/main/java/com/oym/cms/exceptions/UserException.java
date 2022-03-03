package com.oym.cms.exceptions;

/**
 * 用户异常类
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:16
 */
public class UserException extends RuntimeException {
    private static final long serialVersionUID = 7688456332310845557L;

    public UserException(String message) {
        super(message);
    }
}
