package com.example.springbootstage.service.system;

import com.example.springbootstage.dao.system.QuartzDao;
import com.example.springbootstage.entity.system.Quartz;
import com.example.springbootstage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuartzService implements BaseService<Quartz> {

    @Autowired
    QuartzDao quartzDao;

    @Override
    public Quartz save(Quartz quartz) {
        return null;
    }

    @Override
    public Quartz getById(Long id) {
        return null;
    }

    @Override
    public List<Quartz> getAll() {
        List<Quartz> list = new ArrayList<>();
        quartzDao.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public void delById(Long id) {

    }
}
