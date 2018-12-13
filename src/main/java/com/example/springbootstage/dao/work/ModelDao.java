package com.example.springbootstage.dao.work;

import com.example.springbootstage.entity.work.Model;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ModelDao extends JpaSpecificationExecutor<Model>, PagingAndSortingRepository<Model, Long> {
}
