package com.example.springbootstage.service.work;

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
    public Brand save(Brand brand) {
        return brandDao.save(brand);
    }

    @Override
    public Brand getById(Long id) {
        Optional<Brand> optional = brandDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Brand> getAll() {
        Iterable<Brand> iterable = brandDao.findAll();
        List<Brand> brandList = new LinkedList<>();
        iterable.forEach(brandList::add);
        return brandList;
    }

    @Override
    public void delById(Long id) {
        brandDao.deleteById(id);
    }

    public Brand getByName(String name) {
        return brandDao.findByName(name);
    }
}
