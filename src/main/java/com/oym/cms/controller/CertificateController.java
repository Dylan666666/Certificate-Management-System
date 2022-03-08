package com.oym.cms.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oym.cms.dto.CertificateDTO;
import com.oym.cms.entity.Certificate;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.exceptions.CertificateException;
import com.oym.cms.service.CertificateService;
import com.oym.cms.uitl.HttpServletRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

        try {
            CertificateDTO res = certificateService.addCertificate(certificate);
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
        if (userId == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.EMPTY_INPUT_STR.getStatusInfo());
            LOGGER.error("CertificateController queryCertificateByUserId empty userId");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        try {
            CertificateDTO certificateDTOList = certificateService.queryCertificateByUserId(userId, pageIndex, pageSize);
            CertificateDTO certificateDTOCount = certificateService.queryCertificateCountByUserId(userId);
            if (certificateDTOList.getMsg() != DTOMsgEnum.OK.getStatus() 
                    || certificateDTOCount.getMsg() != DTOMsgEnum.OK.getStatus()) {
                modelMap.put("success", false);
                modelMap.put("errMsg", DTOMsgEnum.stateOf(certificateDTOList.getMsg()).getStatusInfo() +
                        " || " +  DTOMsgEnum.stateOf(certificateDTOCount.getMsg()).getStatusInfo());
                LOGGER.info("CertificateController queryCertificateByUserId fail userId:{}", userId);
                return modelMap;
            }
            LOGGER.info("CertificateController queryCertificateByUserId success userId:{}", userId);
            modelMap.put("CertificateList", certificateDTOList.getCertificateList());
            modelMap.put("certificateCount", certificateDTOCount.getCount());
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

    /**
     * 模糊查询用户拥有的证书
     * @param request
     * @return
     */
    @PostMapping("/certificate/userQueryByCondition")
    @RequiresPermissions("/certificate/userQueryByCondition")
    public Map<String,Object> queryCertificateByCondition(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String certificateConditionStr = HttpServletRequestUtil.getString(request, "certificateCondition");
        if (StringUtils.isEmpty(certificateConditionStr)) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.EMPTY_INPUT_STR.getStatusInfo());
            LOGGER.error("CertificateController queryCertificateByCondition empty certificateCondition");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Certificate certificateCondition = null;
        try {
            certificateCondition = mapper.readValue(certificateConditionStr, Certificate.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_INPUT_STR.getStatusInfo());
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10;
        }
        try {
            CertificateDTO certificateDTOList = certificateService.queryCertificateByCondition(certificateCondition, pageIndex, pageSize);
            CertificateDTO certificateDTOCount = certificateService.queryCertificateCountByCondition(certificateCondition);
            if (certificateDTOList.getMsg() != DTOMsgEnum.OK.getStatus()
                    || certificateDTOCount.getMsg() != DTOMsgEnum.OK.getStatus()) {
                modelMap.put("success", false);
                modelMap.put("errMsg", DTOMsgEnum.stateOf(certificateDTOList.getMsg()).getStatusInfo() +
                        " || " +  DTOMsgEnum.stateOf(certificateDTOCount.getMsg()).getStatusInfo());
                LOGGER.info("CertificateController queryCertificateByCondition fail certificateCondition:{}",
                        JSON.toJSONString(certificateCondition));
                return modelMap;
            }
            LOGGER.info("CertificateController queryCertificateByCondition success certificateCondition:{}", 
                    JSON.toJSONString(certificateCondition));
            modelMap.put("CertificateList", certificateDTOList.getCertificateList());
            modelMap.put("certificateCount", certificateDTOCount.getCount());
            modelMap.put("success", true);
        } catch (CertificateException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_EXCEPTION.getStatusInfo());
            LOGGER.error("CertificateController queryCertificateByCondition error certificateCondition:{}", 
                    JSON.toJSONString(certificateCondition));
            return modelMap;
        } catch (Exception e ){
            modelMap.put("success", false);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_EXCEPTION.getStatusInfo());
            LOGGER.error("CertificateController queryCertificateByCondition error certificateCondition:{}", 
                    JSON.toJSONString(certificateCondition));
            return modelMap;
        }
        return modelMap;
    }
    
}
