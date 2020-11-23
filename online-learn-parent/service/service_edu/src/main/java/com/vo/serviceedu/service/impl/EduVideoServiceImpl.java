package com.vo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.serviceedu.client.VodClient;
import com.vo.serviceedu.entity.EduVideo;
import com.vo.serviceedu.mapper.EduVideoMapper;
import com.vo.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.select("video_source_id");

        boolean removeVideoFlag = this.deleteVideoVodByVideoQueryWrapper(videoQueryWrapper);
        if (!removeVideoFlag) {
            throw new VoCodeException(20001, "删除课程下的小节失败");
        }
    }

    @Override
    public boolean deleteVideoByChapterId(String chapterId) {
        //1.根据章节ID查询章节下小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        videoQueryWrapper.select("video_source_id");
        return this.deleteVideoVodByVideoQueryWrapper(videoQueryWrapper);

    }

    /**
     * 根据EduVideo的查询条件删除小节和视频文件
     *
     * @param queryWrapper
     */
    private boolean deleteVideoVodByVideoQueryWrapper(QueryWrapper<EduVideo> queryWrapper) {
        //.查询所有的video
        List<EduVideo> videos = this.list(queryWrapper);
        List<String> videoSourceIds = new ArrayList<>();
        for (int i = 0; i < videos.size(); i++) {
            String videoSourceId = videos.get(i).getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIds.add(videoSourceId);
            }
        }
        //2.如果最终的videoSourceIds的size不等于0，则调用vodClient进行删除
        if (videoSourceIds.size() > 0) {
            vodClient.deleteVideoByIds(videoSourceIds);
        }
        return this.remove(queryWrapper);
    }
}
