package com.oym.cms.mapper;

import com.oym.cms.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:08
 */
public interface UserMapper {
    /**
     * 通过账号查询用户
     *
     * @param userId
     * @return
     */
    User queryUserById(@Param("userId") String userId);

    /**
     * 登录
     *
     * @param userId
     * @param userPassword
     * @return
     */
    User userLogin(@Param("userId") String userId, @Param("userPassword") String userPassword);
}
