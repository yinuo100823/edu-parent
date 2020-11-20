package com.vo.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.serviceedu.entity.EduVideo;
import com.vo.serviceedu.mapper.EduVideoMapper;
import com.vo.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public void deleteVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);

        //删除小节时需要删除小节下的视频文件 TODO
        boolean removeVideoFlag = this.remove(videoQueryWrapper);
        if (!removeVideoFlag) {
            throw new VoCodeException(20001, "删除课程下的小节失败");
        }
    }
}
