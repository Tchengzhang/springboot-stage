package com.example.springbootstage.dao;

import com.example.springbootstage.entity.Brand;
import org.springframework.data.repository.CrudRepository;

public interface BrandDao extends CrudRepository<Brand, Long> {
}
