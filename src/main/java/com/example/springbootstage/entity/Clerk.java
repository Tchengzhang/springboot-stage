package com.example.springbootstage.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Clerk extends BaseRowModel {
    @Id
    @GeneratedValue
    @ExcelProperty(value = {"id"}, index = 0)
    private Long id;
    @ExcelProperty(value = {"姓名"}, index = 1)
    private String name;
    @ExcelProperty(value = {"电话号码"}, index = 2)
    private String phone;
    @ExcelProperty(value = {"性别"}, index = 3)
    private String gender;
    @ExcelProperty(value = {"状态"}, index = 4)
    private String status;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "store_id")
    private Store store;


    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updateUser;
}
