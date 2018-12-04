package com.example.springbootstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String province;

    private String city;

    private String district;

    private String detail;

    private String longitude;

    private String latitude;

    private String zoneCode;

    private String status;

    @ManyToMany
    @JoinTable(name = "ModelStore", joinColumns = {@JoinColumn(name = "sid")}, inverseJoinColumns = {@JoinColumn(name = "mid")})
    private List<Model> modelList;

    @ManyToMany
    @JoinTable(name = "StorePackage", joinColumns = {@JoinColumn(name = "sid")}, inverseJoinColumns = {@JoinColumn(name = "pid")})
    private List<Package> packageList;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updateUser;


}
