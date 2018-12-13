package com.example.springbootstage.entity.work;


import com.example.springbootstage.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Model extends BaseEntity {


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



}
