package com.example.springbootstage.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //id

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String modelCode;

    private String price;

    private String name;

    private String type;

    private String status;

    @ManyToMany
    @JoinTable(name = "ModelPackage", joinColumns = {@JoinColumn(name = "mid")}, inverseJoinColumns = {@JoinColumn(name = "pid")})
    private List<Package> packageList;

    @ManyToMany
    @JoinTable(name = "ModelStore", joinColumns = {@JoinColumn(name = "mid")}, inverseJoinColumns = {@JoinColumn(name = "sid")})
    private List<Store> storeList;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updateUser;


}
