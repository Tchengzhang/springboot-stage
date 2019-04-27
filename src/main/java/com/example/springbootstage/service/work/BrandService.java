package com.example.springbootstage.service.work;

import com.example.springbootstage.annotation.TargetDateSource;
import com.example.springbootstage.config.DataSourceConfig;
import com.example.springbootstage.dao.work.BrandDao;
import com.example.springbootstage.entity.work.Brand;
import com.example.springbootstage.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements BaseService<Brand> {

    @Autowired
    private BrandDao brandDao;

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public Brand save(Brand brand) {
        return brandDao.save(brand);
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public Brand getById(Long id) {
        Optional<Brand> optional = brandDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public List<Brand> getAll() {
        Iterable<Brand> iterable = brandDao.findAll();
        List<Brand> brandList = new LinkedList<>();
        iterable.forEach(brandList::add);
        return brandList;
    }

    @Override
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public void delById(Long id) {
        brandDao.deleteById(id);
    }

    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public Brand getByName(String name) {
        return brandDao.findByName(name);
    }
}
