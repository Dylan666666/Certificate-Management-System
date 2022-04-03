package com.oym.cms.uitl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片处理工具类
 * @Author: Mr_OO
 * @Date: 2022/3/31 21:13
 */
public class ImageUtil {
    
    public static final String IMAGE_URL_PRE = "http://101.43.139.237:8081/";

    /**
     * 日期格式
     */
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 随机数生成对象
     */
    private static final Random random = new Random();
    
    /**
     * 生成随机文件名，当前年月日小时分秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        //获取随机的五位数
        int ranNum = random.nextInt(89999) + 10000;
        //当前年月日小时分秒
        String nowTimeStr = sDateFormat.format(new Date()).substring(7);
        return nowTimeStr + ranNum;
    }

    /**
     * 获取输入流的扩展名
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获取图片远程访问URL后缀
     * @param result
     * @return
     */
    public static String getImageUrl(String[] result) {
        if (result.length < 2) {
            return null;
        } else {
            return result[0] + "/" + result[1];
        }
    }
    
}
