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
     * 更新账户信息
     *
     * @param user
     * @return
     */
    int updateUser(User user);
    
}
