package com.oym.cms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 证书信息类
 * @Author: Mr_OO
 * @Date: 2022/3/2 15:04
 */
public class Certificate implements Serializable {
    private static final long serialVersionUID = 7383159382779976173L;

    /**
     * 证书名称
     */
    private String certificateName;

    /**
     * 证书序列号
     */
    private String certificateId;

    /**
     * 证书类别
     */
    private Integer certificateType;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 证书获奖时间
     */
    private Date certificateWinTime;

    /**
     * 证书描述
     */
    private String certificateDescription;

    /**
     * 证书凭证URL
     */
    private String certificateUrl;

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public String getCertificateDescription() {
        return certificateDescription;
    }

    public void setCertificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public Integer getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCertificateWinTime() {
        return certificateWinTime;
    }

    public void setCertificateWinTime(Date certificateWinTime) {
        this.certificateWinTime = certificateWinTime;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "certificateName='" + certificateName + '\'' +
                ", certificateId='" + certificateId + '\'' +
                ", certificateType=" + certificateType +
                ", userId='" + userId + '\'' +
                ", certificateWinTime=" + certificateWinTime +
                ", certificateDescription='" + certificateDescription + '\'' +
                '}';
    }
}
