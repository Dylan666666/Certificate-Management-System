package com.oym.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.oym.cms.dto.UserDTO;
import com.oym.cms.entity.User;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.enums.UserIdStatusEnum;
import com.oym.cms.exceptions.UserException;
import com.oym.cms.mapper.UserMapper;
import com.oym.cms.service.UserService;
import com.oym.cms.uitl.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/3 18:13
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * UserServiceImpl log
     */
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
        LOGGER.info("UserServiceImpl userLogin userId:{}", userId);
        if (userId != null && userPassword != null) {
            try {
                User userQuery = userMapper.queryUserById(userId);
                if (userQuery == null) {
                    LOGGER.info("UserServiceImpl userLogin userId:{}, Nonexistent Account", userId);
                    return new UserDTO(null, DTOMsgEnum.ACCOUNT_NOT_EXIST.getStatus());
                }
                if (UserIdStatusEnum.INVALID.getStatus().equals(userQuery.getUserStatus())) {
                    LOGGER.info("UserServiceImpl userLogin userId:{}, invalid Account", userId);
                    return new UserDTO(null, DTOMsgEnum.ACCOUNT_INVALID.getStatus());
                }
                // 获取Subject实例对象，用户实例
                Subject currentUser = SecurityUtils.getSubject();
                // 将用户名和密码封装到UsernamePasswordToken
                UsernamePasswordToken token = new UsernamePasswordToken(userId, userPassword);
                // 传到 MyShiroRealm 类中的方法进行认证
                currentUser.login(token);
                // 构建缓存用户信息返回给前端
                User user = (User) currentUser.getPrincipals().getPrimaryPrincipal();
                //设置TOKEN返回给前端
                user.setUserToken(currentUser.getSession().getId().toString());
                LOGGER.info("UserServiceImpl userLogin userId:{}, success", userId);
                return new UserDTO(user, DTOMsgEnum.OK.getStatus());
            } catch (UnknownAccountException e) {
                LOGGER.error("UserServiceImpl queryUserById error userId:{}", userId);
                return new UserDTO(null, DTOMsgEnum.ACCOUNT_NOT_EXIST.getStatus());
            } catch (IncorrectCredentialsException e) {
                LOGGER.error("UserServiceImpl queryUserById error userId:{}", userId);
                return new UserDTO(null, DTOMsgEnum.WRONG_PASSWORD.getStatus());
            } catch (AuthenticationException e) {
                LOGGER.error("UserServiceImpl queryUserById error userId:{}", userId);
                return new UserDTO(null, DTOMsgEnum.LOGIN_FAIL.getStatus());
            }catch (UserException e) {
                LOGGER.error("UserServiceImpl queryUserById error userId:{}", userId);
                return new UserDTO(null, DTOMsgEnum.LOGIN_FAIL.getStatus());
            }
        }
        return new UserDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }

    @Override
    public UserDTO updateUser(User user) {
        if (user != null && user.getUserId() != null) {
            try {
                User userQuery = userMapper.queryUserById(user.getUserId());
                if (userQuery == null) {
                    LOGGER.info("UserServiceImpl updateUser user:{}, Nonexistent Account",  JSON.toJSONString(user));
                    return new UserDTO(null, DTOMsgEnum.ACCOUNT_NOT_EXIST.getStatus());
                }
                //更改密码逻辑
                if (user.getUserPassword() != null && user.getId() != null) {
                    if (!userQuery.getId().equals(user.getId())) {
                        LOGGER.info("UserServiceImpl updateUser user:{}, ID WRONG",  JSON.toJSONString(user));
                        return new UserDTO(null, DTOMsgEnum.WRONG_ID.getStatus());
                    }
                    //对新密码进行加密
                    PasswordHelper.encryptPassword(user);
                }
                int res = userMapper.updateUser(user);
                if (res == 0) {
                    LOGGER.info("UserServiceImpl updateUser user:{}, update fail",  JSON.toJSONString(user));
                    return new UserDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
                }
                return new UserDTO(user, DTOMsgEnum.OK.getStatus());
            } catch (UserException e) {
                LOGGER.error("UserServiceImpl updateUser exception user:{}", JSON.toJSONString(user));
                return new UserDTO(null, DTOMsgEnum.ERROR_EXCEPTION.getStatus());
            }
        }
        return new UserDTO(null, DTOMsgEnum.EMPTY_INPUT_STR.getStatus());
    }
}
