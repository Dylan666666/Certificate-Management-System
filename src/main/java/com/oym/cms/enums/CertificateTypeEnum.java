package com.oym.cms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/5 20:09
 */
public enum CertificateTypeEnum {
    SCHOLARSHIP_AND_HONORARY_TITLE(1001, "奖学金及荣誉称号类"),
    DISCIPLINE_COMPETITION(1002, "学科竞赛类"),
    QUALIFICATION_LEVEL(1003, "资格等级类"),
    VOLUNTARY_SERVICE(1004, "志愿服务类"),
    ART_EXHIBITION(1005, "艺术展览类"),
    SPORTS_COMPETITION(1006, "体育竞技类"),
    OTHER(1007, "其他类");

    private Integer status;
    private String statusInfo;

    CertificateTypeEnum(Integer status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public static CertificateTypeEnum stateOf(int number) {
        for (CertificateTypeEnum value : values()) {
            if (value.getStatus() == number) {
                return value;
            }
        }
        return null;
    }
}
