package com.vo.serviceedu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
public class CourseQuery implements Serializable {

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;

    @ApiModelProperty(value = "课程讲师")
    private String teacherId;

    @ApiModelProperty(value = "二级分类ID")
    private String subjectId;

    @ApiModelProperty(value = "一级分类ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程状态：Draft（未发布）；Normal（已发布）")
    private String status;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;


}
