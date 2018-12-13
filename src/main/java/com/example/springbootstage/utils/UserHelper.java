package com.example.springbootstage.utils;


import org.apache.shiro.SecurityUtils;

/**
 * Created by WangHong on 2018/3/26.
 */
public class UserHelper {

    /**
     * 获取当前登录用户信息.
     */
    public static String getCurrentUser() {
        return (String)SecurityUtils.getSubject().getPrincipal();
    }

}
