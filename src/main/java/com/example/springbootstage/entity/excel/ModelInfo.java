package com.example.springbootstage.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class ModelInfo extends BaseRowModel {

    @ExcelProperty(value = {"id"}, index = 0)
    private Long id;    //id
    @ExcelProperty(value = {"品牌"}, index = 1)
    private String brand;
    @ExcelProperty(value = {"编码"}, index = 2)
    private String modelCode;
    @ExcelProperty(value = {"价格"}, index = 3)
    private String price;
    @ExcelProperty(value = {"机型"}, index = 4)
    private String name;
    @ExcelProperty(value = {"类型"}, index = 5)
    private String type;
    @ExcelProperty(value = {"状态"}, index = 6)
    private String status;
}
