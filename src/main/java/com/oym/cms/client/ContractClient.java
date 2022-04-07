package com.oym.cms.client;

import com.oym.cms.contract.CertificateIQ;
import com.oym.cms.entity.Certificate;
import com.oym.cms.enums.DTOMsgEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 合约接口调用类
 * @Author: Mr_OO
 * @Date: 2022/4/6 12:03
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContractClient {
    
    private CertificateIQ certificateIQ;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractClient.class);
    
    private final static int SUCCESS_RET_Int = 0;
    private final static String SUCCESS_RET_String = "0";

    /**
     * 证书注册到区块链上
     * @param schoolFlag
     * @param stuNumber
     * @param userName
     * @param cmsName
     * @param cmsType
     * @param cmsWinTime
     * @param cmsDesc
     * @param cmsUrl
     * @return
     */
    public int registerCertificate(String schoolFlag, String stuNumber, String userName, String cmsName,
                                     String cmsType, String cmsWinTime, String cmsDesc, String cmsUrl) {
        try {
            TransactionReceipt receipt = certificateIQ.register(schoolFlag, stuNumber, userName, cmsName, cmsType, 
                    cmsWinTime, cmsDesc, cmsUrl);
            Tuple1<BigInteger> registerOutput = certificateIQ.getRegisterOutput(receipt);
            if (receipt.getStatus().equals(SUCCESS_RET_String)) {
                if (Objects.equals(registerOutput.getValue1(), BigInteger.valueOf(SUCCESS_RET_Int))) {
                    LOGGER.info("ContractClient registerCertificate success cmsUrl:{}", cmsUrl);
                    return DTOMsgEnum.OK.getStatus();
                }
            }
        } catch (Exception e) {
            LOGGER.info("ContractClient registerCertificate exception cmsUrl:{}, e:{}", cmsUrl, e.getMessage());
        }
        LOGGER.info("ContractClient registerCertificate fail cmsUrl:{}", cmsUrl);
        return DTOMsgEnum.ERROR_EXCEPTION.getStatus();
    }

    /**
     * 通过学号查询证书集合
     * @param userId
     * @return
     */
    public List<Certificate> queryCertificateList(String userId) {
        try {
            Tuple6<BigInteger, List<String>, List<String>, List<String>, List<String>, List<String>> result = certificateIQ.select(userId);
            //判断是否集合为空
            if (!Objects.equals(result.getValue1(), BigInteger.valueOf(0))) {
                LOGGER.info("ContractClient registerCertificate success userId:{}", userId);
                //开始封装
                List<String> list1 = result.getValue2();
                List<String> list2 = result.getValue3();
                List<String> list3 = result.getValue4();
                List<String> list4 = result.getValue5();
                List<String> list5 = result.getValue6();
                List<Certificate> certificateList = new ArrayList<>(list1.size());
                for (int i = 0; i < certificateList.size(); i++) {
                    Certificate certificate = new Certificate();
                    certificate.setUserId(userId);
                    certificate.setCertificateName(list1.get(i));
                    certificate.setCertificateType(Integer.valueOf(list2.get(i)));
                    certificate.setCertificateWinTime(new Date(list3.get(i)));
                    certificate.setCertificateDescription(list4.get(i));
                    certificate.setCertificateUrl(list5.get(i));
                    certificateList.add(certificate);
                }
                return certificateList;
            }
        } catch (Exception e) {
            LOGGER.info("ContractClient queryCertificateList exception userId:{}", userId);
        }
        return null;
    }
    
}
