package com.example.springbootstage.dao;

import com.example.springbootstage.entity.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelDao extends CrudRepository<Model, Long> {
}
