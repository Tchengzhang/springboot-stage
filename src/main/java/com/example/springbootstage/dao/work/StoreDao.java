package com.example.springbootstage.dao.work;


import com.example.springbootstage.entity.work.Store;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StoreDao extends JpaSpecificationExecutor<Store>, PagingAndSortingRepository<Store, Long> {
}
