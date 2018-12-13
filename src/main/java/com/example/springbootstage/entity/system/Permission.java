package com.example.springbootstage.entity.system;

import com.example.springbootstage.entity.BaseEntity;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * 许可Bean
 * Created by WangHong on 2018/3/21.
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "permission")
@Data
public class Permission extends BaseEntity {

    /**
     * 删除（0-否， 1-是）
     */
    private int state = 0;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String remark;
    /**
     * 父项
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Permission parent;
    /**
     * 子集合
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private List<Permission> children = Lists.newArrayList();


    @Override
    public String toString() {
        return "Permission{" +
                "state=" + state +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", children=" + children.size() +
                '}';
    }
}
