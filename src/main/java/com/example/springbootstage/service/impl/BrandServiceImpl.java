package com.example.springbootstage.service.impl;

import com.example.springbootstage.dao.BrandDao;
import com.example.springbootstage.entity.Brand;
import com.example.springbootstage.service.BrandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    public void save(Brand brand) {
        brandDao.save(brand);
    }

    public Brand getById(Long id) {
        Optional<Brand> optional = brandDao.findById(id);
        return optional.orElse(null);
    }

    public List<Brand> getAll() {
        Iterable<Brand> iterable = brandDao.findAll();
        List<Brand> brandList = new ArrayList<>();
        iterable.forEach(brandList::add);
        return brandList;
    }
}
