package com.vo.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.serviceedu.entity.EduSubject;
import com.vo.serviceedu.entity.Excel.SubjectData;
import com.vo.serviceedu.entity.subject.FirstSubject;
import com.vo.serviceedu.entity.subject.SecondSubject;
import com.vo.serviceedu.listener.SubjectExcelListener;
import com.vo.serviceedu.mapper.EduSubjectMapper;
import com.vo.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Loong
 * @since 2020-11-10
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            //获取文件输入流
            InputStream in = file.getInputStream();

            //读取文件
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<FirstSubject> getSubjectTree() {
        //1.获取所有的一级分类
        QueryWrapper<EduSubject> queryFirstWrapper = new QueryWrapper<>();
        queryFirstWrapper.eq("parent_id", "0");

        List<EduSubject> eduFirstSubjects = this.list(queryFirstWrapper);
        //2.获取所有的二级分类
        QueryWrapper<EduSubject> querySecondWrapper = new QueryWrapper<>();
        queryFirstWrapper.ne("parent_id", "0");

        List<EduSubject> eduSecondSubjects = this.list(querySecondWrapper);
        //3.封装
        //3.1创建一个集合，存储最终返回的数据
        List<FirstSubject> subjectTree = new ArrayList<>();
        //3.2遍历所有的一级分类，添加到FirstSubject中
        for (int i = 0; i < eduFirstSubjects.size(); i++) {
            EduSubject eduFirstSubject = eduFirstSubjects.get(i);
            FirstSubject firstSubject = new FirstSubject();
            BeanUtils.copyProperties(eduFirstSubject, firstSubject);
            subjectTree.add(firstSubject);

            //最终返回的二级分类集合
            List<SecondSubject> secondSubjects = new ArrayList<>();
            for (int j = 0; j < eduSecondSubjects.size(); j++) {

                EduSubject eduSecondSubject = eduSecondSubjects.get(j);
                SecondSubject secondSubject = new SecondSubject();
                BeanUtils.copyProperties(eduSecondSubject, secondSubject);
                if (eduSecondSubject.getParentId().equals(firstSubject.getId())) {
                    secondSubjects.add(secondSubject);
                }

            }
            firstSubject.setChildren(secondSubjects);

        }
        return subjectTree;
    }
}
