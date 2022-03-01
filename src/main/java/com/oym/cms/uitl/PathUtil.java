package com.oym.cms.uitl;

import com.oym.cms.common.Path;

/**
 * 图片存储路径配置类
 * @Author: Mr_OO
 * @Date: 2022/3/1 13:52
 */
public class PathUtil {

    /**
     * 获取基础路径
     * @return
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        String windows = "windows";
        if(os.toLowerCase().startsWith(windows)) {
            basePath = Path.WIN_PATH;
        } else {
            basePath = Path.LINUX_PATH;
        }
        return basePath;
    }

    /**
     * 获取具体子路径
     *
     * @param certificateId
     * @return
     */
    public static String getSpecificPath(int certificateId) {
        String specificPath = Path.CERTIFICATE_PATH + certificateId +  "/";
        return specificPath;
    }
}
