package com.oym.cms.PO;

/**
 * 区块链证书存储实体对应类
 * @Author: Mr_OO
 * @Date: 2022/4/1 12:27
 */
public class BCOSCertificatePO {
    
    private String schoolFlag;
    private String ID;
    private String stuNumber;
    private String userName;
    private String cmsName;
    private Integer cmsType;
    private String cmsWinTime;
    private String cmsDesc;
    private String cmsUrl;

    public Integer getCmsType() {
        return cmsType;
    }

    public void setCmsType(Integer cmsType) {
        this.cmsType = cmsType;
    }

    public String getSchoolFlag() {
        return schoolFlag;
    }

    public void setSchoolFlag(String schoolFlag) {
        this.schoolFlag = schoolFlag;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCmsName() {
        return cmsName;
    }

    public void setCmsName(String cmsName) {
        this.cmsName = cmsName;
    }

    public String getCmsWinTime() {
        return cmsWinTime;
    }

    public void setCmsWinTime(String cmsWinTime) {
        this.cmsWinTime = cmsWinTime;
    }

    public String getCmsDesc() {
        return cmsDesc;
    }

    public void setCmsDesc(String cmsDesc) {
        this.cmsDesc = cmsDesc;
    }

    public String getCmsUrl() {
        return cmsUrl;
    }

    public void setCmsUrl(String cmsUrl) {
        this.cmsUrl = cmsUrl;
    }
}
