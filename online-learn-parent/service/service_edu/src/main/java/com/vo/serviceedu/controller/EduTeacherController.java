package com.vo.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vo.commonutils.Resp;
import com.vo.commonutils.ResultCode;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.serviceedu.entity.EduTeacher;
import com.vo.serviceedu.entity.vo.TeacherQuery;
import com.vo.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Loong
 * @since 2020-11-06
 */
@Api("讲师管理")
@RestController
@RequestMapping("/edu/service/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/list")
    public Resp allTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return Resp.ok().data("data", list);

    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("delete/{id}")
    public Resp deleteById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return Resp.ok().message("删除成功");
        }
        return Resp.error().message("删除失败");
    }

    @ApiOperation(value = "根据查询条件分页获取讲师")
    @PostMapping("list/{pageNo}/{pageSize}")
    public Resp getTeacherListByPage(
            @PathVariable long pageNo,
            @PathVariable long pageSize,
            @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(pageNo, pageSize);

        //查询条件带分页功能
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(pageTeacher, queryWrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> teachers = pageTeacher.getRecords();
        Map<String, Object> respMap = new HashMap<>();
        respMap.put("total", total);
        respMap.put("rows", teachers);
        return Resp.ok().data("data", respMap);

    }

    @PostMapping("/save")
    @ApiOperation(value = "添加讲师")
    public Resp addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher) {
        if (StringUtils.isEmpty(eduTeacher.getAvatar())
                || StringUtils.isEmpty(eduTeacher.getCareer())
                || StringUtils.isEmpty(eduTeacher.getIntro())
                || StringUtils.isEmpty(eduTeacher.getName())) {
            throw new VoCodeException(ResultCode.ERROR, "参数不全");

        }
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return Resp.ok().message("保存成功");

        } else {
            return Resp.error().message("保存失败");
        }

    }

    @ApiOperation(value = "根据讲师id获取讲师信息")
    @GetMapping("info/{id}")
    public Resp getTeacherById(
            @ApiParam(name = "id", value = "根据id获取讲师信息", required = true)
            @PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        if (teacher == null) {
            throw new VoCodeException(20002, "查询的讲师信息不存在");
        }
        return Resp.ok().data("data", eduTeacherService.getById(id));
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新讲师")
    public Resp updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.updateById(eduTeacher);
        if (save) {
            return Resp.ok().message("保存成功");

        } else {
            return Resp.error().message("保存失败");
        }

    }
}