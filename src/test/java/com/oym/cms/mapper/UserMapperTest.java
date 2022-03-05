package com.oym.cms.mapper;

import com.oym.cms.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/4 20:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    
    @Resource
    private UserMapper userMapper;
    
    @Test
    public void queryById() {
        User user = userMapper.queryUserById("2019110117");
        System.out.println(user.toString());
    }
    
}
