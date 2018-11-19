package com.example.springbootstage.service;


import com.example.springbootstage.entity.UserInfo;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    UserInfo findByUsername(String username);
}