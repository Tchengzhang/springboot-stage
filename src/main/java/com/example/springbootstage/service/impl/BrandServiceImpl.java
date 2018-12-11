package com.example.springbootstage.service.impl;

import com.example.springbootstage.dao.BrandDao;
import com.example.springbootstage.entity.Brand;
import com.example.springbootstage.service.BrandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

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

    @Override
    public Brand getByName(String name) {
        return brandDao.findByName(name);
    }
}
