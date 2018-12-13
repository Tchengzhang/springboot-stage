package com.example.springbootstage.dao.work;

import com.example.springbootstage.entity.work.Brand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrandDao extends JpaSpecificationExecutor<Brand>, PagingAndSortingRepository<Brand, Long> {
    Brand findByName(String name);
}
