package com.example.springbootstage.dao;


import com.example.springbootstage.entity.Quartz;
import org.springframework.data.repository.CrudRepository;


public interface QuartzDao extends CrudRepository<Quartz,Long> {
}
