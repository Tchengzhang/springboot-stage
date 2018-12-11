package com.example.springbootstage.dao;

import com.example.springbootstage.entity.Brand;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BrandDao extends CrudRepository<Brand, Long> {
    Brand findByName(String name);
}
