package com.oym.cms.service;

import com.oym.cms.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:13
 */
public interface UserService {
    /**
     * 通过账号查询用户
     *
     * @param userId
     * @return
     */
    UserDTO queryUserById(@Param("userId") String userId);

    /**
     * 登录
     *
     * @param userId
     * @param userPassword
     * @return
     */
    UserDTO userLogin(@Param("userId") String userId, @Param("userPassword") String userPassword);
}
