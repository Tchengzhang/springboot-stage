package com.example.springbootstage.dao.system;


import com.example.springbootstage.entity.system.Quartz;
import org.springframework.data.repository.CrudRepository;


public interface QuartzDao extends CrudRepository<Quartz,Long> {
}
