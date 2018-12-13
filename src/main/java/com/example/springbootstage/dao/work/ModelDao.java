package com.example.springbootstage.dao.work;

import com.example.springbootstage.entity.work.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelDao extends CrudRepository<Model, Long> {
}
