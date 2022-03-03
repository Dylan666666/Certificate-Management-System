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
    private Integer certificateCategory;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 证书图片（图片字节码）
     */
    private String certificateImageStr;

    /**
     * 证书获奖时间
     */
    private Date awardDate;

    public String getCertificateImageStr() {
        return certificateImageStr;
    }

    public void setCertificateImageStr(String certificateImageStr) {
        this.certificateImageStr = certificateImageStr;
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

    public Integer getCertificateCategory() {
        return certificateCategory;
    }

    public void setCertificateCategory(Integer certificateCategory) {
        this.certificateCategory = certificateCategory;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }
}
