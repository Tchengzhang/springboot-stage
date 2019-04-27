package com.example.springbootstage.service.work;

import com.example.springbootstage.annotation.TargetDateSource;
import com.example.springbootstage.config.DataSourceConfig;
import com.example.springbootstage.dao.work.ModelDao;
import com.example.springbootstage.dao.work.PackageDao;
import com.example.springbootstage.dao.work.StoreDao;
import com.example.springbootstage.entity.work.Model;
import com.example.springbootstage.entity.work.Store;
import com.example.springbootstage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService implements BaseService<Store> {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private PackageDao packageDao;

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public Store save(Store entity) {
        entity.getPackageList().clear();
        for (String id : entity.getPackageIds()) {
            entity.getPackageList().add(packageDao.findById(Long.valueOf(id)).get());
        }
        return storeDao.save(entity);
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public Store getById(Long id) {
        Optional<Store> optional = storeDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public List<Store> getAll() {
        Iterable<Store> it = storeDao.findAll();
        List<Store> storeList = new LinkedList<>();
        it.forEach(storeList::add);
        return storeList;
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public void delById(Long id) {
        storeDao.deleteById(id);
    }
}
