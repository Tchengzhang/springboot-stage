package com.example.springbootstage.dao.work;

import com.example.springbootstage.entity.work.Package;
import org.springframework.data.repository.CrudRepository;

public interface PackageDao extends CrudRepository<Package, Long> {
}
