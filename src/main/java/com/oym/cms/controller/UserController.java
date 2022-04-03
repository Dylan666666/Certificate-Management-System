package com.oym.cms.controller;

import com.oym.cms.dto.UserDTO;
import com.oym.cms.entity.User;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.service.UserService;
import com.oym.cms.uitl.HttpServletRequestUtil;
import com.oym.cms.uitl.PasswordHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/4 10:00
 */
@CrossOrigin
@RestController
public class UserController {
    @Resource
    private UserService userService;

    /**
     * UserController log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 登录
     * @param request
     * @return
     */
    @PostMapping("/user/login")
    public Map<String,Object> userLogin(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String userId = HttpServletRequestUtil.getString(request, "userId");
        String userPassword = HttpServletRequestUtil.getString(request, "userPassword");
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userPassword)) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.EMPTY_INPUT_STR.getStatusInfo());
            return modelMap;
        }
        try {
            LOGGER.info("UserController userLogin userId:{}", userId);
            UserDTO userDTO = userService.userLogin(userId, userPassword);
            if (DTOMsgEnum.OK.getStatus() != userDTO.getMsg()) {
                modelMap.put("success",false);
                modelMap.put("errMsg", DTOMsgEnum.stateOf(userDTO.getMsg()).getStatusInfo());
                LOGGER.info("UserController userLogin fail userId:{}, info:{}", 
                        userId, DTOMsgEnum.stateOf(userDTO.getMsg()).getStatusInfo());
                return modelMap;
            }
            userDTO.getUser().setUserPassword("");
            LOGGER.info("UserController userLogin success userId:{}", userId);
            modelMap.put("user", userDTO.getUser());
            modelMap.put("success",true);
        } catch (Exception e) {
            modelMap.put("success",false);
            LOGGER.error("UserController userLogin exception userId:{}", userId);
            modelMap.put("errMsg", DTOMsgEnum.LOGIN_FAIL.getStatusInfo());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 更改密码
     * @param request
     * @return
     */
    @PostMapping("/user/changUserPassword")
    public Map<String,Object> userChangePassword(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String userId = HttpServletRequestUtil.getString(request, "userId");
        String ID = HttpServletRequestUtil.getString(request, "ID");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(ID)|| StringUtils.isEmpty(newPassword)) {
            modelMap.put("success",false);
            modelMap.put("errMsg", DTOMsgEnum.EMPTY_INPUT_STR.getStatusInfo());
            return modelMap;
        }
        try {
            LOGGER.info("UserController userChangePassword userId:{}", userId);
            UserDTO userDTOQuery = userService.queryUserById(userId);
            if (DTOMsgEnum.OK.getStatus() != userDTOQuery.getMsg()) {
                LOGGER.info("UserController userChangePassword exception userId:{}", userId);
                modelMap.put("success", false);
                modelMap.put("errMsg", DTOMsgEnum.stateOf(userDTOQuery.getMsg()).getStatusInfo());
            }
            User user = new User();
            user.setUserId(userId);
            user.setUserPassword(newPassword);
            user.setId(ID);
            UserDTO userDTOUpdate = userService.updateUser(user);
            if (DTOMsgEnum.OK.getStatus() != userDTOUpdate.getMsg()) {
                modelMap.put("success",false);
                modelMap.put("errMsg", DTOMsgEnum.stateOf(userDTOUpdate.getMsg()).getStatusInfo());
                LOGGER.info("UserController userChangePassword fail userId:{}, info:{}",
                        userId, DTOMsgEnum.stateOf(userDTOUpdate.getMsg()).getStatusInfo());
                return modelMap;
            }
            LOGGER.info("UserController userChangePassword success userId:{}", userId);
            modelMap.put("success",true);
        } catch (Exception e) {
            modelMap.put("success",false);
            LOGGER.error("UserController userChangePassword exception userId:{}", userId);
            modelMap.put("errMsg", DTOMsgEnum.ERROR_EXCEPTION.getStatusInfo());
            return modelMap;
        }
        return modelMap;
    }
    
    /**
     * 用户退出系统
     *
     * @param request
     */
    @PostMapping("/logout")
    public void staffLogout(HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipals().getPrimaryPrincipal();
            LOGGER.info("UserController staffLogout userId:{}", user.getUserId());
            subject.logout();
        } catch (Exception e) {}
    }
    
}
