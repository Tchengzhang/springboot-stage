package com.example.springbootstage.service.work;

import com.example.springbootstage.annotation.TargetDateSource;
import com.example.springbootstage.config.DataSourceConfig;
import com.example.springbootstage.dao.work.ModelDao;
import com.example.springbootstage.dao.work.PackageDao;
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

    @Autowired
    private PackageDao packageDao;

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public Model save(Model entity) {
        entity.getPackageList().clear();
        for (String id : entity.getPackageIds()) {
            entity.getPackageList().add(packageDao.findById(Long.valueOf(id)).get());
        }
        return modelDao.save(entity);
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public Model getById(Long id) {
        Optional<Model> optional = modelDao.findById(id);
        return optional.orElse(new Model());
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public List<Model> getAll() {
        Iterable<Model> it = modelDao.findAll();
        List<Model> modelList = new LinkedList<>();
        it.forEach(modelList::add);
        return modelList;
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public void delById(Long id) {
        modelDao.deleteById(id);
    }
}
