package com.example.springbootstage.entity.work;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.springbootstage.entity.BaseEntity;;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Package extends BaseEntity {

    @ExcelProperty(value = {"套餐"}, index = 1)
    private String name;
    @ExcelProperty(value = {"类型"}, index = 2)
    private String type;
    @ExcelProperty(value = {"状态"}, index = 3)
    private String status; //1 启用，0 停用

/*    @ManyToMany
    @JoinTable(name = "ModelPackage", joinColumns = {@JoinColumn(name = "pid")}, inverseJoinColumns = {@JoinColumn(name = "mid")})
    private List<Model> modelList;*/

/*    @ManyToMany
    @JoinTable(name = "StorePackage", joinColumns = {@JoinColumn(name = "pid")}, inverseJoinColumns = {@JoinColumn(name = "sid")})
    private List<Store> storeList;*/


}
