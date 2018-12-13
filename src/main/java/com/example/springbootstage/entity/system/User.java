package com.example.springbootstage.entity.system;

import com.example.springbootstage.entity.BaseEntity;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

/**
 * 用户bean
 * Created by WangHong on 2018/3/21.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@Data
public class User extends BaseEntity {

    /** 删除（0-否， 1-是） */
    private int state = 0;
    /** 用户名. */
    @Column(unique = true)
    private String username;
    /** 密码. */
    private String password;
    /** 姓名. */
    private String nickname;

    private String salt;//加密密码的盐
    /** 角色集合 */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {@JoinColumn(name = "role_id") })
    @OrderBy("id")
    private Set<Role> roles = Sets.newLinkedHashSet();

    @Transient
    public Set<String> getRoleNames(){
        Set<String> roleNames = Sets.newLinkedHashSet();
        for(Role role : this.getRoles()){
            roleNames.add(role.getName());
        }
        return roleNames;
    }

    @Transient
    public Set<String> getPermissionNames(){
        Set<String> permissionNames = Sets.newLinkedHashSet();
        for(Role role : this.getRoles()){
            for(Permission permission : role.getPermissions()){
                permissionNames.add(permission.getName());
            }
        }
        return permissionNames;
    }
    /**
     * 密码盐.
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }
    //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解



}
