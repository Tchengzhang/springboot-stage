package com.example.springbootstage.entity.work;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.example.springbootstage.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Store extends BaseEntity {

    @ExcelProperty(value = {"名称"}, index = 1)
    private String name;
    @ExcelProperty(value = {"省"}, index = 2)
    private String province;
    @ExcelProperty(value = {"市"}, index = 3)
    private String city;
    @ExcelProperty(value = {"区"}, index = 4)
    private String district;
    @ExcelProperty(value = {"详细地址"}, index = 5)
    private String detail;
    @ExcelProperty(value = {"经度"}, index = 6)
    private String longitude;
    @ExcelProperty(value = {"纬度"}, index = 7)
    private String latitude;
    @ExcelProperty(value = {"区域编码"}, index = 8)
    private String zoneCode;

/*    @ManyToMany
    @JoinTable(name = "ModelStore", joinColumns = {@JoinColumn(name = "sid")}, inverseJoinColumns = {@JoinColumn(name = "mid")})
    private Set<Model> modelList = Sets.newLinkedHashSet();

    @Transient
    private Set<String> modelIds = Sets.newLinkedHashSet();*/
    @ManyToMany
    @JoinTable(name = "StorePackage", joinColumns = {@JoinColumn(name = "sid")}, inverseJoinColumns = {@JoinColumn(name = "pid")})
    private Set<Package> packageList = Sets.newLinkedHashSet();

    @Transient
    private Set<String> packageIds = Sets.newLinkedHashSet();


}
