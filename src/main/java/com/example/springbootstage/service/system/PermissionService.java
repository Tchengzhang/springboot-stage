package com.example.springbootstage.service.system;


import com.example.springbootstage.dao.system.PermissionDao;
import com.example.springbootstage.entity.system.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 许可Service
 * Created by WangHong on 2018/4/2.
 */
@Service
public class PermissionService {

    @Resource
    private PermissionDao permissionDao;

    @Transactional(readOnly = true)
    public List<Permission> findByParentIsNull() {
        return permissionDao.findByParentIsNull();
    }
}
