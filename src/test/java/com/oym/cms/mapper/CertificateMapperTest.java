package com.oym.cms.mapper;

import com.oym.cms.entity.Certificate;
import com.oym.cms.enums.CertificateTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/5 19:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CertificateMapperTest {
    
    @Resource
    private CertificateMapper certificateMapper;
    
    @Test
    public void insertTest() {
        Certificate certificate = new Certificate();
        certificate.setUserId("2019110117");
        certificate.setCertificateId("20191101171");
        certificate.setCertificateName("英语CET-4证书");
        certificate.setCertificateDescription("大学英语四级证书");
        certificate.setCertificateType(CertificateTypeEnum.QUALIFICATION_LEVEL.getStatus());
        certificate.setCertificateWinTime(new Date());
        
        int result = certificateMapper.addCertificate(certificate);
        System.out.println("添加证书情况：" + result);
    }
    
    @Test
    public void queryTest() {
        List<Certificate> list = certificateMapper.queryCertificateByUserId("2019110117");
        System.out.println(list.size());
        System.out.println(list.get(0).toString());
        int count = certificateMapper.queryCertificateCountByUserId("2019110117");
        System.out.println(count);
    }
    
}
