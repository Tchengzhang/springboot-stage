package com.example.springbootstage.service.impl;

import com.example.springbootstage.dao.ModelDao;
import com.example.springbootstage.entity.Model;
import com.example.springbootstage.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelDao modelDao;

    @Override
    public Model save(Model model) {
        return modelDao.save(model);
    }

    @Override
    public Model getById(Long id) {
        Optional<Model> optional = modelDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Model> getAll() {
        Iterable<Model> it = modelDao.findAll();
        List<Model> modelList = new LinkedList<>();
        it.forEach(modelList::add);
        return modelList;
    }

    @Override
    public void delById(Long id) {
        modelDao.deleteById(id);
    }
}
