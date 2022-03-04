package com.oym.cms.config.shiro;

import com.oym.cms.dto.UserDTO;
import com.oym.cms.entity.User;
import com.oym.cms.enums.DTOMsgEnum;
import com.oym.cms.mapper.JurisdictionMapper;
import com.oym.cms.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Mr_OO
 * @Date: 2022/3/3 17:21
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private JurisdictionMapper jurisdictionMapper;
    @Resource
    private UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //取出职工功能表数据
        List<String> urlList = jurisdictionMapper.queryAllUrls();
        //授权
        for (String s : urlList) {
            authorizationInfo.addStringPermission(s);
        }
        return authorizationInfo;
    }

    /**
     * 身份认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的账号.
        String userId = (String) token.getPrincipal();

        //通过userId从数据库中查找 User对象.
        UserDTO userDTO = userService.queryUserById(userId);
        if (DTOMsgEnum.OK.getStatus() != userDTO.getMsg()) {
            return null;
        }

        return new SimpleAuthenticationInfo(
                //传入对象（一定要是对象！！！getPrimaryPrincipal()要取得）
                userDTO.getUser(),
                //传入的是加密后的密码
                userDTO.getUser().getUserPassword(),
                //传入salt
                ByteSource.Util.bytes(userDTO.getUser().getUserId()),
                getName());
    }
}
