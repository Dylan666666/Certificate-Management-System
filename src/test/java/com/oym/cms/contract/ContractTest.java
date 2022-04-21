package com.oym.cms.contract;

import com.alibaba.fastjson.JSON;
import com.oym.cms.client.ContractClient;
import com.oym.cms.entity.Certificate;
import com.oym.cms.enums.DTOMsgEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2022/4/10 21:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractTest {
    
    @Resource
    private ContractClient contractClient;
    
    @Test
    public void insertTest() {
        String schoolFlag = "sicnu";
        String stuNumber = "2018110428";
        String userName = "罗胜";
        String cmsName = "英语CET-4证书";
        String cmsType = "1003";
        String cmsWinTime = String.valueOf(new Date().getTime());
        String cmsDesc = "大学英语四级证书";
        String cmsUrl = "/group1/M00/00/00/CgAYCGJS1fyABmBfAAACk-NVe_Q056.png";
        int res = contractClient.registerCertificate(schoolFlag, stuNumber, userName, cmsName, cmsType, 
                cmsWinTime, cmsDesc, cmsUrl);
        System.out.println("插入结果为：" + DTOMsgEnum.stateOf(res).getStatusInfo());
    }

    @Test
    public void queryTest() {
        List<Certificate> res =  contractClient.queryCertificateList("2018110429");
        System.out.println("查询结果为：" + JSON.toJSONString(res));
    }
}
