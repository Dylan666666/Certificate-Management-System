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
     * @param pageIndex
     * @param pageSize
     * @return
     */
    CertificateDTO queryCertificateByUserId(String userId, int pageIndex, int pageSize);


    /**
     * 查询该用户已存储的证书数量
     * @param userId
     * @return
     */
    CertificateDTO queryCertificateCountByUserId(String userId);

    /**
     * 模糊查询该用户已存储的证书
     * @param certificateCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    CertificateDTO queryCertificateByCondition(Certificate certificateCondition, int pageIndex, int pageSize);
    
}
