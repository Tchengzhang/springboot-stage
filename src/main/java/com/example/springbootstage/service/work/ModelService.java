package com.example.springbootstage.service.work;

import com.example.springbootstage.dao.work.ModelDao;
import com.example.springbootstage.entity.work.Model;
import com.example.springbootstage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ModelService implements BaseService<Model> {

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
