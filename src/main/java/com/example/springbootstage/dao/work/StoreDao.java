package com.example.springbootstage.dao.work;


import com.example.springbootstage.entity.work.Store;
import org.springframework.data.repository.CrudRepository;

public interface StoreDao extends CrudRepository<Store, Long> {
}
