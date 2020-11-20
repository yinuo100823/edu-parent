package com.vo.serviceedu.mapper;

import com.vo.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vo.serviceedu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishInfoById(String courseId);

}
