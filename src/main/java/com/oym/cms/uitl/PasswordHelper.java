package com.oym.cms.uitl;

import com.oym.cms.entity.User;
import com.oym.cms.enums.UserIdStatusEnum;
import com.oym.cms.enums.UserPositionEnum;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码生成工具
 * @Author: Mr_OO
 * @Date: 2022/3/4 9:41
 */
public class PasswordHelper {
    private final static String ALGORITHM_NAME = "MD5";
    private final static int HASH_ITERATIONS = 20;

    public static void main(String[] args) {
        User user = new User();
        user.setUserId("2019110117");
        user.setUserName("梁莉丹");
        user.setUserPosition(UserPositionEnum.STUDENT.getStatus());
        user.setUserStatus(UserIdStatusEnum.OK.getStatus());
        
        //密码生成
        String newPassword = new SimpleHash(ALGORITHM_NAME, "20010908",
                ByteSource.Util.bytes(user.getUserId()), HASH_ITERATIONS).toHex();
        System.out.println("加密后的密码是：" + newPassword);
    }
    
}
