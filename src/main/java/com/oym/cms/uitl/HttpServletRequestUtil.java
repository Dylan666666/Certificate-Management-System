package com.oym.cms.uitl;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Request中的键值对数据
 * @Author: Mr_OO
 * @Date: 2022/3/1 13:48
 */
public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1000;
        }
    }

    public static long getLong(HttpServletRequest request,String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1000L;
        }
    }

    public static double getDouble(HttpServletRequest request,String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1000d;
        }
    }

    public static boolean getBoolean(HttpServletRequest request,String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request,String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                result = result.trim();
            }
            if("".equals(result)) {
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
