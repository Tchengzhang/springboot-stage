package com.example.springbootstage.dao.system;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.example.springbootstage.entity.system.Role;

import java.util.List;

/**
 * 角色Dao接口
 * Created by WangHong on 2018/3/27.
 */
public interface RoleDao extends PagingAndSortingRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    List<Role> findByState(final int state);

    Role findByIdAndState(final Long id, final int state);
}
