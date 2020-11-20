package com.vo.serviceedu.controller;


import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vo.commonutils.Resp;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.serviceedu.constant.CourseConstant;
import com.vo.serviceedu.entity.EduCourse;
import com.vo.serviceedu.entity.vo.CoursePublishVo;
import com.vo.serviceedu.entity.vo.CourseQuery;
import com.vo.serviceedu.entity.vo.CourseVo;
import com.vo.serviceedu.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
@RestController
@RequestMapping("/edu/service/course")
@CrossOrigin
public class EduCourseController {


    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "根据查询条件分页获取课程")
    @PostMapping("list/{pageNo}/{pageSize}")
    public Resp getCourseListByPage(
            @PathVariable long pageNo,
            @PathVariable long pageSize,
            @RequestBody(required = false) CourseQuery courseQuery) {
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(pageNo, pageSize);

        //查询条件带分页功能
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.like("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title.trim());
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        courseService.page(pageCourse, queryWrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> courses = pageCourse.getRecords();
        Map<String, Object> respMap = new HashMap<>();
        respMap.put("total", total);
        respMap.put("rows", courses);
        return Resp.ok().data("data", respMap);

    }


    @PostMapping("/addCourse")
    public Resp addCourse(@RequestBody CourseVo courseVo) {
        String courseId = courseService.addCourse(courseVo);
        return Resp.ok().data("courseId", courseId);
    }

    /**
     * 根据课程Id获取课程信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("/info/{courseId}")
    public Resp getCourseInfoById(@PathVariable String courseId) {
        CourseVo courseInfo = courseService.getCourseInfoById(courseId);
        return Resp.ok().data("data", courseInfo);
    }

    @PostMapping("/update")
    public Resp updateCourseById(@RequestBody CourseVo course) {
        courseService.updateCourseById(course);
        return Resp.ok();
    }

    @GetMapping("/publish/info/{courseId}")
    public Resp getCoursePublishInfoById(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishInfoById(courseId);
        return Resp.ok().data("data", coursePublishVo);
    }

    @PostMapping("/publish/{courseId}")
    public Resp publishCourseById(@PathVariable String courseId, @RequestBody Map<String, String> status) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        if (status == null) {
            throw new VoCodeException(20001, "发布状态不能为空");
        }
        course.setStatus(status.get("status"));
        boolean isSuccess = courseService.updateById(course);
        return isSuccess ? Resp.ok() : Resp.error().message("发布课程失败");
    }

    @DeleteMapping("/delete/{courseId}")
    public Resp deleteCourseById(@PathVariable String courseId) {
        boolean flag = courseService.deleteCourseById(courseId);
        return flag ? Resp.ok().message("删除课程成功") : Resp.error().message("删除课程失败");

    }

}

