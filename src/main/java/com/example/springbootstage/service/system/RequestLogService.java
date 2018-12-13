package com.example.springbootstage.service.system;

import com.example.springbootstage.dao.system.RequestLogDao;
import com.example.springbootstage.entity.system.RequestLog;
import com.example.springbootstage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestLogService implements BaseService<RequestLog> {

    @Autowired
    RequestLogDao requestLogDao;

    @Override
    public RequestLog save(RequestLog requestLog) {
        return requestLogDao.save(requestLog);
    }

    @Override
    public RequestLog getById(Long id) {
        return null;
    }

    @Override
    public List<RequestLog> getAll() {
        return null;
    }

    @Override
    public void delById(Long id) {

    }
}
