package com.example.springbootstage.entity.system;

import com.example.springbootstage.entity.BaseEntity;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.*;
import java.util.Set;

/**
 * 角色Bean
 * Created by WangHong on 2018/3/21.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role")
@Data
public class Role extends BaseEntity {

    /** 删除（0-否， 1-是） */
    private int state = 0;
    /** 名称 */
    private String name;
    /** 描述 */
    private String remark;
    /** 权限 */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {@JoinColumn(name = "permission_id") })
    @OrderBy("id")
    private Set<Permission> permissions = Sets.newLinkedHashSet();

    /** 临时属性 许可Id集合 */
    @Transient
    private Set<String> permissionIds = Sets.newLinkedHashSet();

}
