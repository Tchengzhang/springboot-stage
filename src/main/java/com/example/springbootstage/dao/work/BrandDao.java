package com.example.springbootstage.dao.work;

import com.example.springbootstage.entity.work.Brand;
import org.springframework.data.repository.CrudRepository;

public interface BrandDao extends CrudRepository<Brand, Long> {
    Brand findByName(String name);
}
