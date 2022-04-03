package com.oym.cms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:28
 */
public enum DTOMsgEnum {
    OK(1, "请求成功"), 
    
    EMPTY_INPUT_STR(-1000, "输入数据为空"),
    ERROR_INPUT_STR(-1001, "输入数据不符合规范"),
    ERROR_EXCEPTION(-1002, "请求失败"),
    
    ACCOUNT_NOT_EXIST(-2001, "账号不存在"),
    WRONG_PASSWORD(-2002, "密码不正确"),
    LOGIN_FAIL(-2003, "用户验证失败"),
    ACCOUNT_INVALID(-2004, "该用户账号已失效"),
    WRONG_ID(-2005,"身份证号码有误");

    private int status;
    private String statusInfo;

    DTOMsgEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public static DTOMsgEnum stateOf(int number) {
        for (DTOMsgEnum value : values()) {
            if (value.getStatus() == number) {
                return value;
            }
        }
        return null;
    }
}
