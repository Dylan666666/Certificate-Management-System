package com.oym.cms.mapper;

import com.oym.cms.entity.Certificate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/5 19:34
 */
public interface CertificateMapper {

    /**
     * 添加证书
     * @param certificate
     * @return
     */
    int addCertificate(Certificate certificate);

    /**
     * 查询该用户已存储的证书
     * @param userId
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Certificate> queryCertificateByUserId(@Param("userId") String userId, 
                                               @Param("rowIndex") int rowIndex,
                                               @Param("pageSize") int pageSize);

    /**
     * 查询该用户已存储的证书数量
     * @param userId
     * @return
     */
    int queryCertificateCountByUserId(@Param("userId") String userId);

    /**
     * 模糊查询该用户已存储的证书
     * @param certificateCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Certificate> queryCertificateByCondition(@Param("certificateCondition") Certificate certificateCondition,
                                               @Param("rowIndex") int rowIndex,
                                               @Param("pageSize") int pageSize);

    /**
     * 模糊查询该用户已存储的证书的数量
     * @param certificateCondition
     * @return
     */
    int queryCertificateCountByCondition(@Param("certificateCondition") Certificate certificateCondition);
}
