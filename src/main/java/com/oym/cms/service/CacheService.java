package com.oym.cms.service;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:13
 */
public interface CacheService {
    /**
     * 依据 key前缀删除匹配该模式下的所有key-value
     * @param keyPrefix
     * @return
     */
    void removeFromCache(String keyPrefix);
}
