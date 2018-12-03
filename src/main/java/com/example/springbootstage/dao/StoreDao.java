package com.example.springbootstage.dao;


import com.example.springbootstage.entity.Store;
import org.springframework.data.repository.CrudRepository;

public interface StoreDao extends CrudRepository<Store, Long> {
}
