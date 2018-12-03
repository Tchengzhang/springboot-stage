package com.example.springbootstage.dao;

import com.example.springbootstage.entity.Package;
import org.springframework.data.repository.CrudRepository;

public interface PackageDao extends CrudRepository<Package, Long> {
}
