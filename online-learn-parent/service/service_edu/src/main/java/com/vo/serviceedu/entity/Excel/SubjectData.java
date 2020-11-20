package com.vo.serviceedu.entity.Excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {

    @ExcelProperty(value = "一级分类",index = 0)
    private String firstSubject;

    @ExcelProperty(value = "二级分类",index = 1)
    private String secondSubject;
}
