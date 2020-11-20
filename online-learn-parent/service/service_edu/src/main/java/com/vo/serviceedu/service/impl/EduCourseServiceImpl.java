package com.vo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.serviceedu.entity.EduChapter;
import com.vo.serviceedu.entity.EduCourse;
import com.vo.serviceedu.entity.EduCourseDescription;
import com.vo.serviceedu.entity.EduVideo;
import com.vo.serviceedu.entity.vo.CoursePublishVo;
import com.vo.serviceedu.entity.vo.CourseVo;
import com.vo.serviceedu.mapper.EduCourseMapper;
import com.vo.serviceedu.service.EduChapterService;
import com.vo.serviceedu.service.EduCourseDescriptionService;
import com.vo.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vo.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService courseDescriptionService;

    @Autowired
    EduChapterService chapterService;

    @Autowired
    EduVideoService videoService;

    @Override
    public String addCourse(CourseVo courseVo) {
        //1.添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        boolean flag = this.save(eduCourse);
        if (!flag) {
            throw new VoCodeException(20001, "添加课程失败");
        }
        //2.添加描述信息
        String courseId = eduCourse.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseVo.getDescription());
        courseDescription.setId(courseId);//课程描述表中的id与课程表中的id一致，一对一的关系
        courseDescriptionService.save(courseDescription);
        return courseId;
    }

    /**
     * 根据课程Id获取课程信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseVo getCourseInfoById(String courseId) {
        CourseVo courseInfo = new CourseVo();
        //1.获取课程信息
        BeanUtils.copyProperties(this.getById(courseId), courseInfo);
        //2.获取课程描述信息
        BeanUtils.copyProperties(courseDescriptionService.getById(courseId), courseInfo);
        return courseInfo;
    }

    @Override
    public void updateCourseById(CourseVo course) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(course, eduCourse);
        eduCourseDescription.setId(course.getId());
        eduCourseDescription.setDescription(course.getDescription());
        boolean updateCourseFlag = this.updateById(eduCourse);
        boolean updateCourseDescFlag = courseDescriptionService.updateById(eduCourseDescription);
        if (!updateCourseFlag || !updateCourseDescFlag) {
            throw new VoCodeException(20001, "更新课程失败");
        }
    }

    @Override
    public CoursePublishVo getCoursePublishInfoById(String courseId) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishInfoById(courseId);
        if (coursePublishVo == null) {
            throw new VoCodeException(20001, "查询课程发布信息失败！");
        }
        return coursePublishVo;
    }

    @Override
    public boolean deleteCourseById(String courseId) {
        //1.删除小节
        videoService.deleteVideoByCourseId(courseId);
        //2.删除章节
        chapterService.deleteChapterByCourseId(courseId);
        //3.删除课程描述
        boolean removeDescFlag = courseDescriptionService.removeById(courseId);
        if (!removeDescFlag) {
            throw new VoCodeException(20001, "删除课程下章节和小节成功，但是课程描述失败");
        }

        //4.删除课程本身
        return this.removeById(courseId);
    }


}
