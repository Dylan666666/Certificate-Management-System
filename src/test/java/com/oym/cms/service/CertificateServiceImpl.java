package com.oym.cms.service;

import com.alibaba.fastjson.JSON;
import com.oym.cms.config.redis.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/7 19:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CertificateServiceImpl {
    
    @Resource
    private CertificateService certificateService;
    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    
    @Test
    public void addTest() {
        
    }
    
    @Test
    public void queryTest() {
        System.out.println(JSON.toJSONString(certificateService
                .queryCertificateByUserId("2018110429", 1001, 0, 5)));
    }
    
    @Test
    public void redisTest() {
        jedisStrings.set("1", "111");
        System.out.println(jedisStrings.get("1"));
    }
    
}
