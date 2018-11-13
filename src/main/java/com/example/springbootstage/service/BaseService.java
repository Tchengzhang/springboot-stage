package com.example.springbootstage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<D extends JpaRepository<T,Long>,T>{

    @Autowired
    private D dao;

    public T findOne(Long id ){return dao.getOne(id);}

    public List<T> findAll(){return dao.findAll();}

    public void save(T entity){dao.save(entity);}

    public void delete(Long id){dao.deleteById(id);}


}
