package com.example.springbootstage.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Listener - 创建日期、修改日期处理
 * Created by WangHong on 2018/3/21.
 */

public class EntityListener {

    /**
     * 保存前处理
     *
     * @param entity
     *            基类
     */
    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
    }

    /**
     * 更新前处理
     *
     * @param entity
     *            基类
     */
    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setUpdateTime(new Date());
    }

}
