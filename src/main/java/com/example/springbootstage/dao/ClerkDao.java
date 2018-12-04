package com.example.springbootstage.dao;

import com.example.springbootstage.entity.Clerk;
import org.springframework.data.repository.CrudRepository;

public interface ClerkDao extends CrudRepository<Clerk, Long> {
}
