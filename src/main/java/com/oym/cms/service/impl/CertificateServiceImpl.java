package com.oym.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.oym.cms.dto.CertificateDTO;
import com.oym.cms.entity.Certificate;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.exceptions.CertificateException;
import com.oym.cms.mapper.CertificateMapper;
import com.oym.cms.service.CertificateService;
import com.oym.cms.uitl.CertificateIdBuildUtil;
import com.oym.cms.uitl.PageCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/5 20:52
 */
@Service
public class CertificateServiceImpl implements CertificateService {

    /**
     * CertificateServiceImpl log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateServiceImpl.class);
    
    @Resource
    private CertificateMapper certificateMapper;


    @Override
    public synchronized CertificateDTO addCertificate(Certificate certificate) throws CertificateException {
        if (certificate != null 
                && certificate.getCertificateDescription() != null 
                && certificate.getCertificateWinTime() != null 
                && certificate.getCertificateName() != null 
                && certificate.getCertificateType() != null 
                && certificate.getUserId() != null) {
            try {
                //获取该用户已获得证书数量
                int count = certificateMapper.queryCertificateCountByUserId(certificate.getUserId());
                //生成新证书序列号
                String certificateId = CertificateIdBuildUtil.bornNewId(certificate.getUserId(), count);
                certificate.setCertificateId(certificateId);
                int res = certificateMapper.addCertificate(certificate);
                if (res == 0) {
                    LOGGER.info("CertificateServiceImpl addCertificate fail certificate:{}", JSON.toJSONString(certificate));
                }
                LOGGER.info("CertificateServiceImpl addCertificate success certificate:{}", JSON.toJSONString(certificate));
                return new CertificateDTO(null, DTOMsgEnum.OK.getStatus());
            } catch (CertificateException e) {
                LOGGER.error("CertificateServiceImpl addCertificate certificate:{}", JSON.toJSONString(certificate));
                return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            }
        }
        return new CertificateDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }

    @Override
    public CertificateDTO queryCertificateByUserId(String userId, int pageIndex, int pageSize) {
        if (userId != null) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<Certificate> certificateList = certificateMapper.queryCertificateByUserId(userId, rowIndex, pageSize);
                LOGGER.info("CertificateServiceImpl queryCertificateByUserId userId:{}, result:{}",
                        userId, JSON.toJSONString(certificateList));
                return new CertificateDTO(DTOMsgEnum.OK.getStatus(), certificateList);
            } catch (CertificateException e) {
                LOGGER.error("CertificateServiceImpl queryCertificateByUserId userId:{}", userId);
                return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            }
        }
        return new CertificateDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }

    @Override
    public CertificateDTO queryCertificateCountByUserId(String userId) {
        if (userId != null) {
            try {
                int count = certificateMapper.queryCertificateCountByUserId(userId);
                if (count < 0) {
                    LOGGER.error("CertificateServiceImpl queryCertificateCountByUserId count<0 userId:{}", userId);
                    return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
                }
                LOGGER.info("CertificateServiceImpl queryCertificateCountByUserId userId:{}, count:{}", userId, count);
                CertificateDTO certificateDTO = new CertificateDTO(DTOMsgEnum.OK.getStatus());
                certificateDTO.setCount(count);
                return certificateDTO;
            } catch (CertificateException e) {
                LOGGER.error("CertificateServiceImpl queryCertificateCountByUserId error userId:{}", userId);
                return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            }
        }
        return new CertificateDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }

    @Override
    public CertificateDTO queryCertificateByCondition(Certificate certificateCondition, int pageIndex, int pageSize) {
        if (certificateCondition != null && certificateCondition.getUserId() != null) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<Certificate> certificateList = certificateMapper
                        .queryCertificateByCondition(certificateCondition, rowIndex, pageSize);
                LOGGER.info("CertificateServiceImpl queryCertificateByCondition certificateCondition:{}, result:{}",
                        JSON.toJSONString(certificateCondition), JSON.toJSONString(certificateList));
                return new CertificateDTO(DTOMsgEnum.OK.getStatus(), certificateList);
            } catch (CertificateException e) {
                LOGGER.error("CertificateServiceImpl queryCertificateByCondition error certificateCondition:{}",
                        JSON.toJSONString(certificateCondition));
                return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            }
        } 
        return new CertificateDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }

    @Override
    public CertificateDTO queryCertificateCountByCondition(Certificate certificateCondition) {
        if (certificateCondition != null && certificateCondition.getUserId() != null) {
            try {
                int count = certificateMapper.queryCertificateCountByCondition(certificateCondition);
                if (count < 0) {
                    LOGGER.error("CertificateServiceImpl queryCertificateCountByCondition count<0 certificateCondition:{}",
                            JSON.toJSONString(certificateCondition));
                    return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
                }
                LOGGER.info("CertificateServiceImpl queryCertificateCountByUserId certificateCondition:{}, count:{}",
                        JSON.toJSONString(certificateCondition), count);
                CertificateDTO certificateDTO = new CertificateDTO(DTOMsgEnum.OK.getStatus());
                certificateDTO.setCount(count);
                return certificateDTO;
            } catch (CertificateException e) {
                LOGGER.error("CertificateServiceImpl queryCertificateCountByUserId error userId:{}", 
                        JSON.toJSONString(certificateCondition));
                return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            }
        }
        return new CertificateDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }
}
