package com.oym.cms.service;

import com.oym.cms.dto.UserDTO;
import com.oym.cms.enums.DTOMsgEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/4 20:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    
    @Resource
    private UserService userService;

    @Test
    public void queryById() {
        UserDTO user = userService.queryUserById("2019110117");
        System.out.println("接口调用情况：" + DTOMsgEnum.stateOf(user.getMsg()).getStatusInfo());
        System.out.println("用户信息为：" + user.getUser().toString());
    }
    
}
