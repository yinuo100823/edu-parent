package com.vo.serviceedu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String firstSubject;
    private String secondSubject;
    private String teacherName;
    private String price;//只用于显示
    private String status;
}
