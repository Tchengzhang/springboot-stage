package com.example.springbootstage.entity.work;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.example.springbootstage.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Brand extends BaseEntity {
    @ExcelProperty(value = {"名称"}, index = 1)
    private String name;   //品牌名称
    @ExcelProperty(value = {"所属公司"}, index =2)
    private String company;//公司


}
