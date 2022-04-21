package com.oym.cms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oym.cms.dto.CertificateDTO;
import com.oym.cms.dto.ImageHolder;
import com.oym.cms.entity.Certificate;
import com.oym.cms.enums.CertificateTypeEnum;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.exceptions.CertificateException;
import com.oym.cms.service.CertificateService;
import com.oym.cms.util.HttpServletRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/6 11:04
 */
@CrossOrigin
@RestController
public class CertificateController {
    @Resource
    private CertificateService certificateService;

    /**
     * UserController log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateController.class);

    /**
     * 存储新证书
     * @param request
     * @return
     */
    @PostMapping("/certificate/addNewOne")
    @RequiresPermissions("/certificate/addNewOne")
    public Map<String,Object> addNewOneCertificate(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String certificateStr = HttpServletRequestUtil.getString(request,"certificate");
        LOGGER.info("CertificateController addNewOneCertificate certificate:{}", certificateStr);
        if (StringUtils.isEmpty(certificateStr)) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.EMPTY_INPUT_STR.getStatusInfo());
            LOGGER.error("CertificateController addNewOneCertificate empty certificate:{}", certificateStr);
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Certificate certificate = null;
        try {
            certificate = mapper.readValue(certificateStr, Certificate.class);
            if (certificate == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", DTOMsgEnum.ERROR_INPUT_STR.getStatusInfo());
                LOGGER.error("CertificateController addNewOneCertificate empty certificate:{}", certificateStr);
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_INPUT_STR.getStatusInfo());
            LOGGER.error("CertificateController addNewOneCertificate error certificate:{}", certificateStr);
            return modelMap;
        }
        ImageHolder thumbnail = null;
        try {
            //获取文件流
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if (resolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("image");
                if (thumbnailFile != null) {
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_IMAGE.getStatusInfo());
            LOGGER.error("CertificateController addNewOneCertificate error certificate:{}", certificateStr);
        }
        try {
            //执行存储证书逻辑
            CertificateDTO res = certificateService.addCertificate(certificate, thumbnail);
            if (res.getMsg() != DTOMsgEnum.OK.getStatus()) {
                modelMap.put("success", false);
                modelMap.put("errMsg", res.getMsg());
                LOGGER.info("CertificateController addNewOneCertificate fail certificate:{}", certificateStr);
                return modelMap;
            }
            LOGGER.info("CertificateController addNewOneCertificate success certificate:{}", certificateStr);
            modelMap.put("success", true);
        } catch (CertificateException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_EXCEPTION.getStatusInfo());
            LOGGER.error("CertificateController addNewOneCertificate error certificate:{}", certificateStr);
            return modelMap;
        } catch (Exception e ){
            modelMap.put("success", false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_EXCEPTION.getStatusInfo());
            LOGGER.error("CertificateController addNewOneCertificate error certificate:{}", certificateStr);
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 查询用户拥有的证书
     * @param request
     * @return
     */
    @PostMapping("/certificate/userQueryById")
    @RequiresPermissions("/certificate/userQueryById")
    public Map<String,Object> queryCertificateByUserId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String userId = HttpServletRequestUtil.getString(request, "userId");
        int certificateType = HttpServletRequestUtil.getInt(request, "certificateType");
        if (userId == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.EMPTY_INPUT_STR.getStatusInfo());
            LOGGER.error("CertificateController queryCertificateByUserId empty userId");
            return modelMap;
        }
        pageIndex = getPageIndex(pageIndex);
        pageSize = getPageSize(pageSize);
        try {
            //先判断证书类型合法性
            if (certificateType == -1000) {
                certificateType = CertificateTypeEnum.ALL_TYPE.getStatus();
            } else {
                checkCertificateType(certificateType);
            }
            CertificateDTO certificateDTOList = certificateService.queryCertificateByUserId(userId, certificateType, pageIndex, pageSize);
            if (certificateDTOList.getMsg() != DTOMsgEnum.OK.getStatus()) {
                modelMap.put("success", false);
                modelMap.put("errMsg", DTOMsgEnum.stateOf(certificateDTOList.getMsg()).getStatusInfo());
                LOGGER.info("CertificateController queryCertificateByUserId fail, certificateType:{}, userId:{}", certificateType, userId);
                return modelMap;
            }
            LOGGER.info("CertificateController queryCertificateByUserId success, certificateType:{}, userId:{}", certificateType, userId);
            modelMap.put("CertificateList", certificateDTOList.getCertificateList());
            modelMap.put("certificateCount", certificateDTOList.getCount());
            modelMap.put("success", true);
        } catch (CertificateException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_EXCEPTION.getStatusInfo());
            LOGGER.error("CertificateController queryCertificateByUserId error userId:{}", userId);
            return modelMap;
        } catch (Exception e ){
            modelMap.put("success", false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_EXCEPTION.getStatusInfo());
            LOGGER.error("CertificateController queryCertificateByUserId error userId:{}", userId);
            return modelMap;
        }
        return modelMap;
    }
    
    private int getPageIndex(int pageIndex) {
        if (pageIndex < 0 || pageIndex > 1000) {
            return 0;
        }
        return pageIndex;
    }

    private int getPageSize(int pageSize) {
        if (pageSize <= 0 || pageSize > 100) {
            return 5;
        }
        return pageSize;
    }
    
    private void checkCertificateType(int type) {
        if (CertificateTypeEnum.stateOf(type) == null) {
            LOGGER.info("CertificateController queryCertificateByUserId fail,no such cmsType");
            throw new CertificateException(DTOMsgEnum.ERROR_INPUT_STR.getStatusInfo());
        }
    }
    
}
