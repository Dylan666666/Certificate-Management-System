package com.oym.cms.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/4 10:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JurisdictionMapperTest {
    
    @Resource
    private JurisdictionMapper jurisdictionMapper;
    
    @Test
    public void queryAllUrls() {
        List<String> list = jurisdictionMapper.queryAllUrls();
        for (String s : list) {
            System.out.println(s);
        }
    }
    
}
