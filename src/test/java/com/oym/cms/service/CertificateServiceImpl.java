package com.oym.cms.service;

import com.oym.cms.dto.CertificateDTO;
import com.oym.cms.entity.Certificate;
import com.oym.cms.enums.CertificateTypeEnum;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.mapper.CertificateMapper;
import com.oym.cms.uitl.CertificateIdBuildUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/7 19:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CertificateServiceImpl {
    
    @Resource
    private CertificateService certificateService;
    @Resource
    private CertificateMapper certificateMapper;
    
    @Test
    public void addTest() {
        Certificate certificate = new Certificate();
        certificate.setUserId("2019110117");
        certificate.setCertificateId(CertificateIdBuildUtil
                .bornNewId("2019110117", certificateMapper.queryCertificateCountByUserId("2019110117")));
        certificate.setCertificateName("英语CET-6证书");
        certificate.setCertificateDescription("大学英语六级证书");
        certificate.setCertificateType(CertificateTypeEnum.QUALIFICATION_LEVEL.getStatus());
        certificate.setCertificateWinTime(new Date());

        CertificateDTO certificateDTO = certificateService.addCertificate(certificate);
        System.out.println(DTOMsgEnum.stateOf(certificateDTO.getMsg()));
    }
    
    @Test
    public void queryTest() {
        CertificateDTO certificateDTO = certificateService.queryCertificateByUserId("2019110117");
        List<Certificate> certificateList = certificateDTO.getCertificateList();
        for (int i = 0; i < certificateList.size(); i++) {
            System.out.println(certificateList.get(i).getCertificateName());
        }
    }
    
}
