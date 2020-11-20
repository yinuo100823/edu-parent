package com.vo.serviceedu.controller;


import com.vo.commonutils.Resp;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.serviceedu.entity.EduChapter;
import com.vo.serviceedu.entity.chapter.ChapterVo;
import com.vo.serviceedu.entity.vo.CourseVo;
import com.vo.serviceedu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
@RestController
@RequestMapping("/edu/service/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //获取所有章节以及小结
    @GetMapping("/list/{courseId}")
    public Resp getAllChapterAndVideo(@PathVariable String courseId) {
        List<ChapterVo> chapters = chapterService.getAllChapterAndVideo(courseId);
        return Resp.ok().data("data", chapters);
    }

    //添加章节
    @PostMapping("/add")
    public Resp addChapter(@RequestBody EduChapter eduChapter) {
        if (StringUtils.isEmpty(eduChapter.getTitle())) {
            throw new VoCodeException(20001, "章节名称不能为空");
        }
        chapterService.save(eduChapter);
        return Resp.ok();
    }

    //根据ID查询章节
    @GetMapping("/info/{chapterId}")
    public Resp getChapterInfoById(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Resp.ok().data("data", eduChapter);
    }

    //修改
    @PostMapping("/update")
    public Resp updateChapter(@RequestBody EduChapter eduChapter) {
        if (StringUtils.isEmpty(eduChapter.getTitle())) {
            throw new VoCodeException(20001, "章节名称不能为空");
        }
        chapterService.updateById(eduChapter);
        return Resp.ok();
    }

    //删除
    @DeleteMapping("delete/{chapterId}")
    public Resp deleteChapterById(@PathVariable String chapterId) {
        Boolean deleteResult = chapterService.deleteChapterById(chapterId);
        if (deleteResult) {
            return Resp.ok();
        } else {
            return Resp.error();
        }

    }

}

