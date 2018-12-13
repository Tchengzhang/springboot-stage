package com.example.springbootstage.dao.work;

import com.example.springbootstage.entity.work.Clerk;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClerkDao extends JpaSpecificationExecutor<Clerk>, PagingAndSortingRepository<Clerk, Long> {
}
