package com.vo.serviceedu.service;

import com.vo.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vo.serviceedu.entity.subject.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Loong
 * @since 2020-11-10
 */
public interface EduSubjectService extends IService<EduSubject> {




    void addSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<FirstSubject> getSubjectTree();
}
