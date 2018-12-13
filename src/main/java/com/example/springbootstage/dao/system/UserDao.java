package com.example.springbootstage.dao.system;


import com.example.springbootstage.entity.system.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by WangHong on 2018/3/26.
 */
public interface UserDao extends JpaSpecificationExecutor<User>, PagingAndSortingRepository<User, Long> {

    User findByUsername(final String username);

    List<User> findByState(final int state);

    User findByUsernameAndIdNot(final String username, final Long id);
}
