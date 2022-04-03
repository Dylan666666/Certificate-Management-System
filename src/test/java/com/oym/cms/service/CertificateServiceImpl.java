package com.oym.cms.service;

import com.oym.cms.dto.CertificateDTO;
import com.oym.cms.entity.Certificate;
import com.oym.cms.enums.CertificateTypeEnum;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.uitl.CertificateIdBuildUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/7 19:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CertificateServiceImpl {
    
    @Resource
    private CertificateService certificateService;
    
    @Test
    public void addTest() {
        
    }
    
    @Test
    public void queryTest() {
        
    }
    
}
