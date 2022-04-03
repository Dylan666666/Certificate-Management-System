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

    /**
     * 对用户密码加密，并将密码注入对象
     * @param user
     */
    public static void encryptPassword(User user) {
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getUserPassword(),
                ByteSource.Util.bytes(user.getUserId()), HASH_ITERATIONS).toHex();
        user.setUserPassword(newPassword);
    }

    /**
     * 获取加密后的新密码
     * @param userId
     * @param userPassword
     * @return
     */
    public static String encryptPassword(String userId, String userPassword) {
        String newPassword = new SimpleHash(ALGORITHM_NAME, userPassword,
                ByteSource.Util.bytes(userId), HASH_ITERATIONS).toHex();
        return newPassword;
    }
    

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
