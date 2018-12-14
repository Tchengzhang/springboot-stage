package com.example.springbootstage.service.system;


import com.example.springbootstage.dao.system.PermissionDao;
import com.example.springbootstage.dao.system.RoleDao;
import com.example.springbootstage.entity.system.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色Service
 * Created by WangHong on 2018/3/27.
 */
@Service
public class RoleService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;

    public void changeStatus(final Long id) {
        Role entity = roleDao.findById(id).get();
        if (entity != null) {
            entity.setState(1);
            roleDao.save(entity);
        }
    }

    public void save(Role entity) {
        entity.getPermissions().clear();
        for (String id : entity.getPermissionIds()) {
            entity.getPermissions().add(permissionDao.findById(Long.valueOf(id)).get());
        }
        roleDao.save(entity);
    }

    @Transactional(readOnly = true)
    public List<Role> findByState(final int state) {
        return roleDao.findByState(state);
    }

    @Transactional(readOnly = true)
    public Role findOne(final Long id) {
        return roleDao.findById(id).orElse(new Role());
    }

    @Transactional(readOnly = true)
    public Role findByIdAndState(final Long id, final int state) {
        return roleDao.findByIdAndState(id, state);
    }
}
