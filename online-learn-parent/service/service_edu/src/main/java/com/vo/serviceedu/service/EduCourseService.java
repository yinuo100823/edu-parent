package com.vo.serviceedu.service;

import com.vo.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vo.serviceedu.entity.vo.CoursePublishVo;
import com.vo.serviceedu.entity.vo.CourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseVo courseVo);

    /**
     * 根据课程Id获取课程信息
     * @param courseId
     * @return
     */
    CourseVo getCourseInfoById(String courseId);

    void updateCourseById(CourseVo course);

    CoursePublishVo getCoursePublishInfoById(String courseId);

    boolean deleteCourseById(String courseId);
}
