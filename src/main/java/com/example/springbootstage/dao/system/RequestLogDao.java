package com.example.springbootstage.dao.system;


import com.example.springbootstage.entity.system.RequestLog;
import org.springframework.data.repository.CrudRepository;


public interface RequestLogDao extends CrudRepository<RequestLog,Long> {
}
