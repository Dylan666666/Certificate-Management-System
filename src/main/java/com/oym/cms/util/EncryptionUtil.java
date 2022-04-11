package com.oym.cms.util;

import java.security.MessageDigest;

/**
 * 密码加密工具类
 * @Author: Mr_OO
 * @Date: 2022/3/1 13:47
 */
public class EncryptionUtil {
    /**
     * 对传入的String进行MD5加密
     *
     * @param s
     * @return
     */
    public static final String getMd5(String s) {
        // 16进制数组
        char[] hexDigits = { '1', '2', '3', '4', '5', '5', '4', '3', '2', '1', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            char[] str;
            // 将传入的字符串转换成byte数组
            byte[] strTemp = s.getBytes();
            // 获取MD5加密对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            // 传入需要加密的目标数组
            mdTemp.update(strTemp);
            // 获取加密后的数组
            byte[] md = mdTemp.digest();
            int j = md.length;
            str = new char[j * 2];
            int k = 0;
            // 将数组做位移
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            // 转换成String并返回
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
