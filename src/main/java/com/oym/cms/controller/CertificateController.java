package com.oym.cms.controller;

import com.oym.cms.service.CertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    
    
}
