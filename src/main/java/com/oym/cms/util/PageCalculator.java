package com.oym.cms.util;

/**
 * 分页查询 —— 行索引计算工具
 * @Author: Mr_OO
 * @Date: 2022/3/8 12:38
 */
public class PageCalculator {
    public static int calculatorRowIndex(int pageIndex, int pageSize) {
        return (pageIndex >= 0) ? pageIndex * pageSize : 0;
    }
}
