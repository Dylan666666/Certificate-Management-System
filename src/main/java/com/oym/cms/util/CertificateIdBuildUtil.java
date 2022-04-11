package com.oym.cms.util;

/**
 * 证书序列号生成器
 * @Author: Mr_OO
 * @Date: 2022/3/5 19:49
 */
public class CertificateIdBuildUtil {

    /**
     * 机构名称
     */
    private static final String ORGANIZATION_NAME = "sicnu";

    /**
     * 生成证书序列号
     * @param userId 证书所有者账户ID
     * @param count 证书所有者已存储证书数量
     * @return
     */
    public static String bornNewId(String userId, int count) {
        StringBuilder result = new StringBuilder();
        result.append(ORGANIZATION_NAME).append(userId).append(count + 1);
        return result.toString();
    }
    
}
