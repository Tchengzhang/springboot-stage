package com.example.springbootstage.service.impl;


import com.example.springbootstage.dao.UserInfoDao;
import com.example.springbootstage.entity.UserInfo;
import com.example.springbootstage.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoDao.save(userInfo);
    }

    @Override
    public UserInfo getById(Long id) {
        return null;
    }

    @Override
    public List<UserInfo> getAll() {
        return null;
    }

    @Override
    public void delById(Long id) {

    }
}