package com.oym.cms.uitl;

import com.oym.cms.PO.BCOSCertificatePO;
import com.oym.cms.entity.Certificate;
import com.oym.cms.entity.User;

import java.util.Date;

/**
 * 实体转化工具类
 * @Author: Mr_OO
 * @Date: 2022/4/1 12:33
 */
public class EntityToPoUtil {

    /**
     * 合成区块链存储实体
     * @param user
     * @param certificate
     * @return
     */
    public static BCOSCertificatePO makeCertificateToPO(User user, Certificate certificate) {
        if (user == null || certificate == null) {
            return null;
        }
        BCOSCertificatePO certificatePO = new BCOSCertificatePO();
        certificatePO.setSchoolFlag(user.getSchoolFlag());
        certificatePO.setUserName(user.getUserName());
        certificatePO.setID(certificate.getCertificateId()); ;
        certificatePO.setStuNumber(certificate.getUserId()); 
        certificatePO.setCmsName(certificate.getCertificateName()); 
        certificatePO.setCmsType(certificate.getCertificateType()); 
        certificatePO.setCmsDesc(certificate.getCertificateDescription()); 
        certificatePO.setCmsUrl(certificate.getCertificateUrl()); 
        return certificatePO;
    }

    /**
     * PO转化为证书实体
     * @param po
     * @return
     */
    public static Certificate getCertificateFromPO(BCOSCertificatePO po) {
        if (po == null) {
            return null;
        }
        Certificate certificate = new Certificate();
        certificate.setCertificateId(po.getID());
        certificate.setCertificateWinTime(new Date(po.getCmsWinTime()));
        certificate.setCertificateType(po.getCmsType());
        certificate.setCertificateDescription(po.getCmsDesc());
        certificate.setCertificateName(po.getCmsName());
        certificate.setUserId(po.getStuNumber());
        certificate.setCertificateUrl(po.getCmsUrl());
        return certificate;
    }
    
}
