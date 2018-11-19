package com.example.springbootstage.service.impl;

import com.example.springbootstage.dao.RequestLogDao;
import com.example.springbootstage.entity.RequestLog;
import com.example.springbootstage.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestLogServiceImpl implements RequestLogService {

    @Autowired
    RequestLogDao requestLogDao;

    @Override
    public void save(RequestLog requestLog) {
        requestLogDao.save(requestLog);
    }
}
