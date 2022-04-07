package com.oym.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.oym.cms.client.ContractClient;
import com.oym.cms.client.FastDFSClient;
import com.oym.cms.dto.CertificateDTO;
import com.oym.cms.dto.ImageHolder;
import com.oym.cms.entity.Certificate;
import com.oym.cms.entity.User;
import com.oym.cms.enums.CertificateTypeEnum;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.exceptions.CertificateException;
import com.oym.cms.mapper.UserMapper;
import com.oym.cms.service.CertificateService;
import com.oym.cms.uitl.ImageUtil;
import com.oym.cms.uitl.PageCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
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
    private ContractClient contractClient;
    @Resource
    private UserMapper userMapper;

    /**
     * 本地缓存引入，防止恶意上传
     */
    private Cache<String, String> dataCache = CacheBuilder.newBuilder()     
            .expireAfterWrite(Duration.ofSeconds(30))
            .maximumSize(10240)     
            .concurrencyLevel(4)   
            .initialCapacity(2048)    
            .build();
    
    @Override
    public CertificateDTO addCertificate(Certificate certificate, ImageHolder thumbnail) throws CertificateException {
        if (certificate != null 
                && certificate.getCertificateDescription() != null 
                && certificate.getCertificateWinTime() != null 
                && certificate.getCertificateName() != null 
                && certificate.getCertificateType() != null 
                && certificate.getUserId() != null) {
            try {
                //先经过本地缓存判断用户是否恶意上传
                String value = dataCache.getIfPresent(certificate.getUserId());
                if (value == null) {
                    dataCache.put(certificate.getUserId(), "v");
                } else {
                    //恶意上传直接跳出方法
                    LOGGER.info("CertificateServiceImpl addCertificate fail, NO_SEND_IMMEDIATELY, certificate:{}", JSON.toJSONString(certificate));
                    return new CertificateDTO(null, DTOMsgEnum.NO_SEND_IMMEDIATELY.getStatus());
                }
                //生成文件名
                String imageName = ImageUtil.getRandomFileName(certificate.getUserId(), thumbnail.getImageName());
                //存储图片到分布式服务器系统
                String[] result = FastDFSClient.uploadFile(thumbnail.getImage(), imageName);
                //result结果示例：[group1, M00/00/00/CgAYCGJFpQKACftIAAAbnjkyo8A652.png], 生成图片URL后半截用于存储
                String imageUrl = ImageUtil.getImageUrl(result);
                if (imageUrl == null) {
                    LOGGER.info("CertificateServiceImpl addCertificate fail,imageUrl get fail certificate:{}", JSON.toJSONString(certificate));
                    return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
                }
                //获取存储证书的用户信息
                User user = userMapper.queryUserById(certificate.getUserId());
                if (user == null) {
                    LOGGER.info("CertificateServiceImpl addCertificate fail, no such user certificate:{}", JSON.toJSONString(certificate));
                    return new CertificateDTO(null, DTOMsgEnum.ACCOUNT_NOT_EXIST.getStatus());
                }
                //存储区块链
                int res = contractClient.registerCertificate(
                        user.getSchoolFlag(), user.getUserId(), user.getUserName(), certificate.getCertificateName(), 
                        String.valueOf(certificate.getCertificateType()), certificate.getCertificateWinTime().toString(), 
                        certificate.getCertificateDescription(), imageUrl);
                if (DTOMsgEnum.OK.getStatus() != res) {
                    LOGGER.info("CertificateServiceImpl addCertificate fail certificate:{}", JSON.toJSONString(certificate));
                    return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
                }
                LOGGER.info("CertificateServiceImpl addCertificate success certificate:{}", JSON.toJSONString(certificate));
                return new CertificateDTO(null, DTOMsgEnum.OK.getStatus());
            } catch (CertificateException e) {
                LOGGER.error("CertificateServiceImpl addCertificate Exception certificate:{}", JSON.toJSONString(certificate));
                return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            } 
        }
        return new CertificateDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }

    @Override
    public CertificateDTO queryCertificateByUserId(String userId, int certificateType, int pageIndex, int pageSize) {
        if (userId != null) {
            try {
                //查询起始索引
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                //查询截止索引边界
                int rowEndIndex = pageIndex + pageSize;
                //查询证书列表
                List<Certificate> certificateList = contractClient.queryCertificateList(userId);
                if (certificateList == null) {
                    LOGGER.error("CertificateServiceImpl queryCertificateByUserId BCOS Exception userId:{}", userId);
                    return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
                }   
                int size = certificateList.size();
                CertificateDTO certificateDTO = new CertificateDTO();
                List<Certificate> res = new ArrayList<>(pageSize);
                if (CertificateTypeEnum.ALL_TYPE.getStatus().equals(certificateType)) {
                    if (pageIndex >= size) {
                        return new CertificateDTO(DTOMsgEnum.OK.getStatus(), new ArrayList<Certificate>());
                    }
                    if (size < rowEndIndex) {
                        rowEndIndex = size;
                    }
                    //注入证书总数量
                    certificateDTO.setCount(size);
                    for (int i = rowIndex; i < rowEndIndex; i++) {
                        res.add(certificateList.get(i));
                    }
                } else {
                    List<Certificate> typeList = new ArrayList<>();
                    for (Certificate certificate : certificateList) {
                        if (certificate.getCertificateType().equals(certificateType)) {
                            typeList.add(certificate);
                        }
                    }
                    int count = typeList.size();
                    //注入该类证书总数量
                    certificateDTO.setCount(count);
                    if (pageIndex >= count) {
                        return new CertificateDTO(DTOMsgEnum.OK.getStatus(), new ArrayList<Certificate>());
                    }
                    if (count < rowEndIndex) {
                        rowEndIndex = size;
                    }
                    for (int i = rowIndex; i < rowEndIndex; i++) {
                        res.add(typeList.get(i));
                    }
                }
                certificateDTO.setCertificateList(res);
                certificateDTO.setMsg(DTOMsgEnum.OK.getStatus());
                return certificateDTO;
            } catch (Exception e) {
                LOGGER.error("CertificateServiceImpl queryCertificateByUserId Exception userId:{}", userId);
                return new CertificateDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            }
        } 
        return new CertificateDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }
}
