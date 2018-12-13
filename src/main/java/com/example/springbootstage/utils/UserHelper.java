package com.example.springbootstage.utils;


import com.example.springbootstage.entity.system.User;
import org.apache.shiro.SecurityUtils;

/**
 * Created by WangHong on 2018/3/26.
 */
public class UserHelper {

    /**
     * 获取当前登录用户信息.
     */
    public static String getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "";
        }
        return user.getUsername();
    }

}
