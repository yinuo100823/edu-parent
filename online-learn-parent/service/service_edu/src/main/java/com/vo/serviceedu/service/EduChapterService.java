package com.vo.serviceedu.service;

import com.vo.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vo.serviceedu.entity.chapter.ChapterVo;
import com.vo.serviceedu.entity.vo.CourseVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getAllChapterAndVideo(String courseId);

    Boolean deleteChapterById(String chapterId);

    void deleteChapterByCourseId(String courseId);
}
