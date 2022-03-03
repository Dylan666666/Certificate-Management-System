package com.oym.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.oym.cms.dto.UserDTO;
import com.oym.cms.entity.User;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.enums.UserIdStatusEnum;
import com.oym.cms.exceptions.UserException;
import com.oym.cms.mapper.UserMapper;
import com.oym.cms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:13
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Resource
    private UserMapper userMapper;
    
    @Override
    public UserDTO queryUserById(String userId) throws UserException {
        LOGGER.info("UserServiceImpl queryUserById userId:{}", userId);
        if (userId != null) {
            try {
                User user = userMapper.queryUserById(userId);
                LOGGER.info("UserServiceImpl queryUserById userId:{}, user:{}", userId, JSON.toJSONString(user));
                if (user == null || UserIdStatusEnum.INVALID.getStatus().equals(user.getUserStatus())) {
                    return new UserDTO(null, DTOMsgEnum.OK.getStatus());
                }
                return new UserDTO(user, DTOMsgEnum.OK.getStatus());
            } catch (UserException e) {
                LOGGER.error("UserServiceImpl queryUserById error userId:{}", userId);
                return new UserDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            }
        } 
        return new UserDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }

    @Override
    public UserDTO userLogin(String userId, String userPassword) throws UserException {
        if (userId != null && userPassword != null) {
            try {
                
            } catch (UserException e) {
                
            }
        }
        return new UserDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }
}
