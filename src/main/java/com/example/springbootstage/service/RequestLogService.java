package com.example.springbootstage.service;


import com.example.springbootstage.dao.RequestLogRepository;
import com.example.springbootstage.entity.RequestLog;
import org.springframework.stereotype.Service;

@Service
public class RequestLogService  extends BaseService<RequestLogRepository,RequestLog>{
}
