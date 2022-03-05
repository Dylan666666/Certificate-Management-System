package com.oym.cms.service;

import com.oym.cms.dto.CertificateDTO;
import com.oym.cms.entity.Certificate;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/5 20:44
 */
public interface CertificateService {
    /**
     * 添加证书
     * @param certificate
     * @return
     */
    CertificateDTO addCertificate(Certificate certificate);

    /**
     * 查询该用户已存储的证书
     * @param userId
     * @return
     */
    CertificateDTO queryCertificateByUserId(String userId);
}
