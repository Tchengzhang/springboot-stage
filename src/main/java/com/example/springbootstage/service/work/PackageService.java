package com.example.springbootstage.service.work;

import com.example.springbootstage.annotation.TargetDateSource;
import com.example.springbootstage.config.DataSourceConfig;
import com.example.springbootstage.dao.work.PackageDao;
import com.example.springbootstage.entity.work.Package;
import com.example.springbootstage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PackageService implements BaseService<Package> {

    @Autowired
    private PackageDao packageDao;

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public Package save(Package p) {
        return packageDao.save(p);
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public Package getById(Long id) {
        Optional<Package> optional = packageDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public List<Package> getAll() {
        Iterable<Package> it = packageDao.findAll();
        List<Package> packageList = new LinkedList<>();
        it.forEach(packageList::add);
        return packageList;
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public void delById(Long id) {
        packageDao.deleteById(id);
    }
}
