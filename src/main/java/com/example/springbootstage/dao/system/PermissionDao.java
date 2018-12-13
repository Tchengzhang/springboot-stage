package com.example.springbootstage.dao.system;

import com.example.springbootstage.entity.system.Permission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 许可Dao接口
 * Created by WangHong on 2018/4/2.
 */
public interface PermissionDao extends PagingAndSortingRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    List<Permission> findByParentIsNull();
}
