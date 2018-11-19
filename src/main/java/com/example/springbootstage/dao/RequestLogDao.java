package com.example.springbootstage.dao;


import com.example.springbootstage.entity.RequestLog;
import org.springframework.data.repository.CrudRepository;


public interface RequestLogDao extends CrudRepository<RequestLog,Long> {
}
