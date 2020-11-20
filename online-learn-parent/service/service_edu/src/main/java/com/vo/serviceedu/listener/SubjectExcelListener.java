package com.vo.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.serviceedu.entity.EduSubject;
import com.vo.serviceedu.entity.Excel.SubjectData;
import com.vo.serviceedu.service.EduSubjectService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //    SubjectExcelListener不能交给spring管理，需要手动new，因此该类中不能注入其他对象
//    不进行数据库操作
    public EduSubjectService subjectService;

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new VoCodeException(2002, "表格数据为空，不能添加");
        }
        EduSubject eduFirstSubject = this.exitFirstSubject(subjectService, subjectData.getFirstSubject());

        if (eduFirstSubject == null) {
            eduFirstSubject = new EduSubject();
            eduFirstSubject.setParentId("0");
            eduFirstSubject.setTitle(subjectData.getFirstSubject());
            subjectService.save(eduFirstSubject);
        }
        String pid = eduFirstSubject.getId();
        EduSubject eduSecondSubject = this.exitSecondSubject(subjectService, subjectData.getSecondSubject(), pid);
        if (eduSecondSubject == null) {
            eduSecondSubject = new EduSubject();
            eduSecondSubject.setParentId(pid);
            eduSecondSubject.setTitle(subjectData.getSecondSubject());
            subjectService.save(eduSecondSubject);
        }
    }

    //    判断一级和二级分类是否存在，不可重复添加
    private EduSubject exitFirstSubject(EduSubjectService eduSubjectService, String title) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");
        return eduSubjectService.getOne(queryWrapper);
    }

    private EduSubject exitSecondSubject(EduSubjectService eduSubjectService, String title, String pid) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", pid);
        return eduSubjectService.getOne(queryWrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
