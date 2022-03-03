package com.oym.cms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:28
 */
public enum DTOMsgEnum {
    OK(1, "请求成功"), 
    EMPTY_INPUT_STR(-1000, "请求参数为空"),
    ERROR_INPUT_STR(-1001, "请求参数不符合规范"),
    ERROR_EXCEPTION(-1002, "程序异常");

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
