package com.example.springbootstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Package {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;

    private String status; //1 启用，0 停用

    @ManyToMany
    @JoinTable(name = "ModelPackage", joinColumns = {@JoinColumn(name = "pid")}, inverseJoinColumns = {@JoinColumn(name = "mid")})
    private List<Model> modelList;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updateUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return Objects.equals(id, aPackage.id) &&
                Objects.equals(name, aPackage.name) &&
                Objects.equals(type, aPackage.type) &&
                Objects.equals(status, aPackage.status) &&
                Objects.equals(modelList, aPackage.modelList) &&
                Objects.equals(createUser, aPackage.createUser) &&
                Objects.equals(createTime, aPackage.createTime) &&
                Objects.equals(updateTime, aPackage.updateTime) &&
                Objects.equals(updateUser, aPackage.updateUser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, type, status, modelList, createUser, createTime, updateTime, updateUser);
    }
}
