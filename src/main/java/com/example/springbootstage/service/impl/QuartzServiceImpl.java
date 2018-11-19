package com.example.springbootstage.service.impl;

import com.example.springbootstage.dao.QuartzDao;
import com.example.springbootstage.entity.Quartz;
import com.example.springbootstage.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    QuartzDao quartzDao;

    @Override
    public List<Quartz> findAll() {
        List<Quartz> list = new ArrayList<>();
        quartzDao.findAll().forEach(e -> list.add(e));
        return list;
    }
}
