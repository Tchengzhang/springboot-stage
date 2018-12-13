package com.example.springbootstage.entity.work;


import com.example.springbootstage.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Model extends BaseEntity {


    private int state = 0;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "brand_id")
    private Brand brand;
    private String modelCode;
    private String price;
    private String name;
    private String type;

    @ManyToMany
    @JoinTable(name = "ModelPackage", joinColumns = {@JoinColumn(name = "mid")}, inverseJoinColumns = {@JoinColumn(name = "pid")})
    private Set<Package> packageList = Sets.newLinkedHashSet();
    @Transient
    private Set<String> packageIds = Sets.newLinkedHashSet();

/*    @ManyToMany
    @JoinTable(name = "ModelStore", joinColumns = {@JoinColumn(name = "mid")}, inverseJoinColumns = {@JoinColumn(name = "sid")})
    private Set<Store> storeList;
    @Transient
    private Set<String> storeIds = Sets.newLinkedHashSet();*/



}
