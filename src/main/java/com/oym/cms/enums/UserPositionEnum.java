package com.oym.cms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/4 9:46
 */
public enum UserPositionEnum {
    STUDENT(1, "学生"),
    TEACHER(-1, "老师");

    private Integer status;
    private String statusInfo;

    UserPositionEnum(Integer status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public static UserPositionEnum stateOf(int number) {
        for (UserPositionEnum value : values()) {
            if (value.getStatus() == number) {
                return value;
            }
        }
        return null;
    }
}
