package com.example.springbootstage.dao;


import com.example.springbootstage.entity.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog,Long> {
}
