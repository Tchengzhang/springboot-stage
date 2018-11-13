package com.example.springbootstage.dao;


import com.example.springbootstage.entity.Quartz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuartzRepository extends JpaRepository<Quartz,Long> {
}
