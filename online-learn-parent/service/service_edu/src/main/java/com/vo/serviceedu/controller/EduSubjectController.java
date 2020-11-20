package com.vo.serviceedu.controller;


import com.vo.commonutils.Resp;
import com.vo.serviceedu.entity.subject.FirstSubject;
import com.vo.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Loong
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/edu/service/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("add")
    public Resp addSubject(MultipartFile file) {
        System.out.println(file);
        eduSubjectService.addSubject(file, eduSubjectService);
        return Resp.ok();
    }

    @GetMapping("list")
    public Resp getSubjectTree() {

        List<FirstSubject> subjectTree = eduSubjectService.getSubjectTree();
        return Resp.ok().data("items", subjectTree);
    }

}

