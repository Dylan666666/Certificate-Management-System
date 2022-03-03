package com.oym.cms.enums;

/**
 * 用户账号状态枚举
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:02
 */
public enum UserIdStatusEnum {
    OK(1, "账号正常"),INVALID(-1, "账号已失效");
    
    private Integer status;
    private String statusInfo;

    UserIdStatusEnum(Integer status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }
    
    public static UserIdStatusEnum stateOf(int number) {
        for (UserIdStatusEnum value : values()) {
            if (value.getStatus() == number) {
                return value;
            }
        }
        return null;
    }
}
