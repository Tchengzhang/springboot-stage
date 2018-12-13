package com.example.springbootstage.dao.work;

import com.example.springbootstage.entity.work.Package;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PackageDao extends JpaSpecificationExecutor<Package>, PagingAndSortingRepository<Package, Long> {
}
