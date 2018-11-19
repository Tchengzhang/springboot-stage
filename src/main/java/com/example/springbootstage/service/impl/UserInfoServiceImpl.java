package com.example.springbootstage.service.impl;


import com.example.springbootstage.dao.UserInfoDao;
import com.example.springbootstage.entity.UserInfo;
import com.example.springbootstage.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}