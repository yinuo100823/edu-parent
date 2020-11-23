package com.vo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.commonutils.Resp;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.serviceedu.client.VodClient;
import com.vo.serviceedu.entity.EduChapter;
import com.vo.serviceedu.entity.EduVideo;
import com.vo.serviceedu.entity.chapter.ChapterVo;
import com.vo.serviceedu.entity.chapter.VideoVo;
import com.vo.serviceedu.entity.vo.CourseVo;
import com.vo.serviceedu.mapper.EduChapterMapper;
import com.vo.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vo.serviceedu.service.EduVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    @Override
    public List<ChapterVo> getAllChapterAndVideo(String courseId) {
        //1.获取所有的chapter
        QueryWrapper<EduChapter> eduChapterWrapper = new QueryWrapper<>();
        eduChapterWrapper.eq("course_id", courseId);
        List<EduChapter> chapters = this.list(eduChapterWrapper);

        //2.获取所有的video
        QueryWrapper<EduVideo> eduVideoWrapper = new QueryWrapper<>();
        eduVideoWrapper.eq("course_id", courseId);
        List<EduVideo> videos = eduVideoService.list(eduVideoWrapper);

        //3.创建一个集合，存放最终返回的数据Chapters
        List<ChapterVo> chapterTree = new ArrayList<>();

        //4. 遍历EduChapter，放入chapterTree中
        for (int i = 0; i < chapters.size(); i++) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapters.get(i), chapterVo);
            //5.遍历videos 并判断其chapter_id是否与chapterVo的相同，如果相同则添加进children中
            List<VideoVo> children = new ArrayList<>();
            for (int j = 0; j < videos.size(); j++) {
                EduVideo video = videos.get(j);
                if (video.getChapterId().equals(chapterVo.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    children.add(videoVo);
                }

            }
            chapterVo.setChildren(children);
            chapterTree.add(chapterVo);
        }
        return chapterTree;
    }

    @Override
    public Boolean deleteChapterById(String chapterId) {
        //1.根据章节ID查询章节下小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        //2.删除课程小节
        boolean removeVideoFlag = eduVideoService.deleteVideoByChapterId(chapterId);
        if (!removeVideoFlag) {
            throw new VoCodeException(20001, "删除章节下小节失败");
        }
        return this.removeById(chapterId);

    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        boolean removeChapterFlag = this.remove(chapterQueryWrapper);
        if (!removeChapterFlag) {
            throw new VoCodeException(20001, "删除课程下删除章节失败");
        }
    }
}
